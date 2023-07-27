package com.example.tteapi.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.tteapi.model.Quote;
import com.example.tteapi.jwt.JWTValidation;
import com.example.tteapi.model.Character;
import com.example.tteapi.model.Favourite;
import com.example.tteapi.repository.CharacterRepository;
import com.example.tteapi.repository.FavouriteRepository;
import com.example.tteapi.repository.QuoteRepository;
import com.example.tteapi.utils.ImageUtils;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class QuoteController {

	JWTValidation jwtValidation = new JWTValidation();

	@Autowired
	QuoteRepository quoteRepository;

	@Autowired
	CharacterRepository characterRepository;

	@Autowired
	FavouriteRepository favouriteRepository;

	@GetMapping("/quotes")
	public ResponseEntity<List<Quote>> getAllQuotes(@RequestParam("jwt") String jwt) {
		boolean isValidToken = jwtValidation.validateToken(jwt);
		if (!isValidToken) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}

		try {
			List<Quote> quotes = new ArrayList<Quote>();

			quoteRepository.findAll().forEach(quotes::add);

			return new ResponseEntity<>(quotes, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/quotes/{id}")
	public ResponseEntity<Quote> getQuoteById(@PathVariable("id") long id, @RequestParam("jwt") String jwt) {
		boolean isValidToken = jwtValidation.validateToken(jwt);
		if (!isValidToken) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}

		Optional<Quote> tutorialData = quoteRepository.findById(id);

		if (tutorialData.isPresent()) {
			return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/quotes/list/")
	public ResponseEntity<List<Quote>> getQuotesByIdList(@RequestParam("value") Long[] ids,
			@RequestParam("jwt") String jwt) {
		boolean isValidToken = jwtValidation.validateToken(jwt);
		if (!isValidToken) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}

		List<Quote> response = new ArrayList<Quote>();

		for (Long i : ids) {
			Optional<Quote> quoteData = quoteRepository.findById(i);
			if (quoteData.isPresent()) {
				response.add(quoteData.get());
			}
		}

		return new ResponseEntity<List<Quote>>(response, HttpStatus.OK);

	}

	@GetMapping("/quotes/quote-of-the-day")
	public ResponseEntity<Quote> getQuoteOfTheDay(@RequestParam("jwt") String jwt) {
		boolean isValidToken = jwtValidation.validateToken(jwt);
		if (!isValidToken) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}

		int dayOfYear = LocalDate.now().getDayOfYear();

		Iterable<Quote> allQuotesIterable = quoteRepository.findAll();
		List<Quote> allQuotes = StreamSupport.stream(allQuotesIterable.spliterator(), false)
				.collect(Collectors.toList());

		if (allQuotes.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		int index = dayOfYear % allQuotes.size();
		Quote randomQuote = allQuotes.get(index);

		return new ResponseEntity<>(randomQuote, HttpStatus.OK);
	}

	@GetMapping("/quotes/quote-of-the-week")
	public ResponseEntity<Quote> getQuoteOfTheWeek(@RequestParam("jwt") String jwt) {
		boolean isValidToken = jwtValidation.validateToken(jwt);
		if (!isValidToken) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}

		try {
			Instant currentInstant = Instant.now();
			ZoneId zoneId = ZoneId.systemDefault();
			LocalDate currentDate = currentInstant.atZone(zoneId).toLocalDate();

			LocalDate firstDayOfWeek = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
			LocalDate lastDayOfWeek = currentDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

			Iterable<Favourite> allFavorites = favouriteRepository.findAll();
			List<Favourite> favoritesInCurrentWeek = StreamSupport.stream(allFavorites.spliterator(), false)
					.filter(fav -> {
						LocalDate dateFavourited = fav.getDateFavourited().toInstant().atZone(zoneId).toLocalDate();
						return dateFavourited.isAfter(firstDayOfWeek.minusDays(1))
								&& dateFavourited.isBefore(lastDayOfWeek.plusDays(1));
					})
					.collect(Collectors.toList());

			Map<Long, Integer> quoteToFavouritesCount = new HashMap<>();

			for (Favourite favourite : favoritesInCurrentWeek) {
				long quoteId = favourite.getQuoteID();
				quoteToFavouritesCount.put(quoteId, quoteToFavouritesCount.getOrDefault(quoteId, 0) + 1);
			}

			Quote quoteOfTheWeek = null;
			int maxFavoritesCount = 0;

			for (Map.Entry<Long, Integer> entry : quoteToFavouritesCount.entrySet()) {
				long quoteId = entry.getKey();
				int favouritesCount = entry.getValue();

				if (favouritesCount > maxFavoritesCount) {
					maxFavoritesCount = favouritesCount;
					quoteOfTheWeek = quoteRepository.findById(quoteId).orElse(null);
				}
			}

			if (quoteOfTheWeek == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(quoteOfTheWeek, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/quotes/quote-of-the-month")
	public ResponseEntity<Quote> getQuoteOfTheMonth(@RequestParam("jwt") String jwt) {
		boolean isValidToken = jwtValidation.validateToken(jwt);
		if (!isValidToken) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}

		try {
			LocalDate currentDate = LocalDate.now();
			LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
			LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());

			Iterable<Favourite> allFavorites = favouriteRepository.findAll();
			List<Favourite> favoritesInCurrentMonth = StreamSupport.stream(allFavorites.spliterator(), false)
					.filter(fav -> {
						LocalDate dateFavourited = fav.getDateFavourited().toInstant().atZone(ZoneId.systemDefault())
								.toLocalDate();
						return dateFavourited.isAfter(firstDayOfMonth.minusDays(1))
								&& dateFavourited.isBefore(lastDayOfMonth.plusDays(1));
					})
					.collect(Collectors.toList());

			Map<Long, Integer> quoteToFavouritesCount = new HashMap<>();

			for (Favourite favourite : favoritesInCurrentMonth) {
				long quoteId = favourite.getQuoteID();
				quoteToFavouritesCount.put(quoteId, quoteToFavouritesCount.getOrDefault(quoteId, 0) + 1);
			}

			Quote quoteOfTheMonth = null;
			int maxFavoritesCount = 0;

			for (Map.Entry<Long, Integer> entry : quoteToFavouritesCount.entrySet()) {
				long quoteId = entry.getKey();
				int favouritesCount = entry.getValue();

				if (favouritesCount > maxFavoritesCount) {
					maxFavoritesCount = favouritesCount;
					quoteOfTheMonth = quoteRepository.findById(quoteId).orElse(null);
				}
			}

			if (quoteOfTheMonth == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(quoteOfTheMonth, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/quotes/random")
	public ResponseEntity<Quote> getRandomQuote(@RequestParam("jwt") String jwt) {
		Iterable<Quote> allQuotesIterable = quoteRepository.findAll();
		List<Quote> allQuotes = StreamSupport.stream(allQuotesIterable.spliterator(), false)
				.collect(Collectors.toList());

		Quote randQuote = allQuotes.get((int) Math.floor(allQuotes.size() * Math.random()));

		return new ResponseEntity<Quote>(randQuote, HttpStatus.OK);
	}

	@PostMapping("/quotes")
	public ResponseEntity<Quote> createQuote(@RequestBody Quote quote, @RequestParam("jwt") String jwt) {
		try {
			Long characterId = quote.getCharacterID();

			Character character = characterRepository.findById(characterId).orElse(null);
			if (character == null) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}

			Quote _quote = quoteRepository
					.save(new Quote(quote.getQuoteText(), quote.getQuoteEp(), quote.getQuoteSeason(),
							character.getId()));

			return new ResponseEntity<>(_quote, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/quotes/batch-create")
	public ResponseEntity<List<Quote>> createQuotes(@RequestBody List<Quote> quotes, @RequestParam("jwt") String jwt) {
		try {
			List<Quote> savedQuotes = new ArrayList<>();
			for (Quote quote : quotes) {
				Long characterId = quote.getCharacterID();

				Character character = characterRepository.findById(characterId).orElse(null);
				if (character == null) {
					return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
				}

				Quote _quote = quoteRepository.save(new Quote(quote.getQuoteText(), quote.getQuoteEp(),
						quote.getQuoteSeason(), character.getId()));
				savedQuotes.add(_quote);
			}
			return new ResponseEntity<>(savedQuotes, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/quotes/{id}")
	public ResponseEntity<HttpStatus> deleteQuote(@PathVariable("id") long id, @RequestParam("jwt") String jwt) {
		try {
			quoteRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/quotes")
	public ResponseEntity<HttpStatus> deleteAllQuotes(@RequestParam("jwt") String jwt) {
		try {
			quoteRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/quote/image")
	public ResponseEntity<byte[]> genQuoteImage() {

		ImageUtils utils = ImageUtils.getInstance();

		Iterable<Quote> allQuotesIterable = quoteRepository.findAll();
		List<Quote> allQuotes = StreamSupport.stream(allQuotesIterable.spliterator(), false)
				.collect(Collectors.toList());

		Quote randQuote = allQuotes.get((int) Math.floor(allQuotes.size() * Math.random()));

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);

		try {
			BufferedImage img = ImageIO.read(QuoteController.class.getClassLoader().getResource("QuoteBackground.png"));

			int width = img.getWidth();
			int height = img.getHeight();

			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = bufferedImage.createGraphics();

			g2d.drawImage(img, 0, 0, null);

			final String owner = characterRepository.findById(randQuote.getCharacterID()).get().getName();

			g2d.setColor(Color.green);
			utils.writeTextToImage(g2d, randQuote.getQuoteText(), owner, width, height);

			g2d.dispose();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", baos);
			byte[] bytes = baos.toByteArray();

			return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);

		} catch (Exception e) {
			System.out.println("Something went wrong generating the quote");
			System.out.println(e.toString());
		}

		return new ResponseEntity<byte[]>(new byte[0], headers, HttpStatus.CREATED);
	}
}
