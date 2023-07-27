package com.example.tteapi.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.tteapi.model.Quote;
import com.example.tteapi.model.Character;
import com.example.tteapi.model.Favourite;
import com.example.tteapi.repository.CharacterRepository;
import com.example.tteapi.repository.FavouriteRepository;
import com.example.tteapi.repository.QuoteRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class QuoteController {

	@Autowired
	QuoteRepository quoteRepository;

	@Autowired
	CharacterRepository characterRepository;

	@Autowired
	FavouriteRepository favouriteRepository;

	@GetMapping("/quotes")
	public ResponseEntity<List<Quote>> getAllQuotes() {
		try {
			List<Quote> quotes = new ArrayList<Quote>();

			quoteRepository.findAll().forEach(quotes::add);

			return new ResponseEntity<>(quotes, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/quotes/{id}")
	public ResponseEntity<Quote> getQuoteById(@PathVariable("id") long id) {
		Optional<Quote> tutorialData = quoteRepository.findById(id);

		if (tutorialData.isPresent()) {
			return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/quotes/quote-of-the-day")
	public ResponseEntity<Quote> getQuoteOfTheDay() {
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
	public ResponseEntity<Quote> getQuoteOfTheWeek() {
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
	public ResponseEntity<Quote> getQuoteOfTheMonth() {
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

	@PostMapping("/quotes")
	public ResponseEntity<Quote> createQuote(@RequestBody Quote quote) {
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
	public ResponseEntity<List<Quote>> createQuotes(@RequestBody List<Quote> quotes) {
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
	public ResponseEntity<HttpStatus> deleteQuote(@PathVariable("id") long id) {
		try {
			quoteRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/quotes")
	public ResponseEntity<HttpStatus> deleteAllQuotes() {
		try {
			quoteRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private final int FontSize = 60;

	private final Font[] fonts = new Font[] { 
		new Font("Roboto", Font.PLAIN, FontSize),
		new Font("Arial", Font.PLAIN, FontSize),
		new Font("Palatino", Font.PLAIN, FontSize),
		new Font("Sans-serif", Font.PLAIN, FontSize),
		new Font("Futura", Font.PLAIN, FontSize),
		new Font("Times New Roman", Font.PLAIN, FontSize),
		new Font("Garamond", Font.PLAIN, FontSize),
		new Font("Helvetica", Font.PLAIN, FontSize)
	};

	private final Color[] fontColors = new Color[]{
		Color.cyan,
		Color.green,
		Color.BLACK,
		Color.red,
		Color.blue
	};

	private int charSpace(int width,int padding){
		width -= padding;
		final int available = width/ padding;
		return available;
	}

	private String[] wordLayout(String Words, int charsAvailable){
		List<String> lstStr = new ArrayList<String>(Arrays.asList(Words.split(" ")));
		List<String> WordMatrix = new ArrayList<String>();

		int currentLength = 0;
		String currentRow = "";
		for (String word : lstStr) {
			currentLength += word.length() + 1;
			if (currentLength <= charsAvailable)
			{
				currentRow += word+" ";
			}
			else
			{
				WordMatrix.add(currentRow.substring(0, currentRow.length()-1));
				currentRow = word +" ";
				currentLength = currentRow.length();
			}
		}
		WordMatrix.add(currentRow.substring(0, currentRow.length()-1));


		String[] strArr = new String[WordMatrix.size()];
		strArr = WordMatrix.toArray(strArr);

		return strArr;
	}

	private void writeTextToImage(Graphics2D img, String text, String quoteOwner,int width,int height){
		//Image Size = 565 x 589
		final Font randomFont = fonts[(int)Math.floor(Math.random()*(fonts.length))];
		final Color fontCol = fontColors[(int)Math.floor(Math.random()*(fontColors.length))];

		final int fontConvertedSize = 30;

		final int spaceAvailable = charSpace(width,fontConvertedSize);
		final String[] wordLayout =wordLayout(text, spaceAvailable);

		img.setFont(randomFont);
		img.setColor(fontCol);

		img.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		int yOffset = (height - FontSize*wordLayout.length)/2;

		for (int x = 0; x < wordLayout.length; x++){
			String line = wordLayout[x];

			int xOffset = (width -  img.getFontMetrics().stringWidth(line))/2;

			img.drawString(((x == 0)?"\"":"")+line+((x == wordLayout.length - 1 )?'"':""), xOffset, yOffset);
			yOffset += FontSize + 10;
		}

		img.drawString("~"+quoteOwner,  (width - quoteOwner.length()*fontConvertedSize)/2, height - FontSize - 20 );
	}

	@GetMapping("/quote/image")
	public ResponseEntity<byte[]> genQuoteImage(){
		
		Iterable<Quote> allQuotesIterable = quoteRepository.findAll();
		List<Quote> allQuotes = StreamSupport.stream(allQuotesIterable.spliterator(), false).collect(Collectors.toList());

		Quote randQuote = allQuotes.get((int)Math.floor(allQuotes.size()*Math.random()));

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);

		try {
			BufferedImage img = ImageIO.read(QuoteController.class.getClassLoader().getResource("QuoteBackground.png"));
			
			int width = img.getWidth();
			int height = img.getHeight();
			
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = bufferedImage.createGraphics();

			g2d.drawImage(img, 0, 0, null);

			final String owner= characterRepository.findById(randQuote.getCharacterID()).get().getName();

			g2d.setColor(Color.green);
			writeTextToImage(g2d,randQuote.getQuoteText(), owner, width, height);

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
