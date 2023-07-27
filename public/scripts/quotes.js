const token = localStorage.getItem('token');
const userId = localStorage.getItem('user');
const queryParams = new URLSearchParams({
  jwt: token,
});

quoteOfTheDay = {
  url: 'https://tteapi-4-env.eba-7w3sxei8.af-south-1.elasticbeanstalk.com/api/quotes/quote-of-the-day',
  text: 'quoteOfTheDayText',
  author: 'quoteOfTheDayAuthor',
  seasonEp: 'quoteOfTheDaySeasonEp',
  quoteId: undefined,
};

quoteOfTheWeek = {
  url: 'https://tteapi-4-env.eba-7w3sxei8.af-south-1.elasticbeanstalk.com/api/quotes/quote-of-the-week',
  text: 'quoteOfTheWeekText',
  author: 'quoteOfTheWeekAuthor',
  seasonEp: 'quoteOfTheWeekSeasonEp',
  quoteId: undefined,
};

quoteOfTheMonth = {
  url: 'https://tteapi-4-env.eba-7w3sxei8.af-south-1.elasticbeanstalk.com/api/quotes/quote-of-the-month',
  text: 'quoteOfTheMonthText',
  author: 'quoteOfTheMonthAuthor',
  seasonEp: 'quoteOfTheMonthSeasonEp',
  quoteId: undefined,
};

getQuote(quoteOfTheDay);
getQuote(quoteOfTheWeek);
getQuote(quoteOfTheMonth);

function httpGet(url) {
  return fetch(url + '?' + queryParams)
    .then(function (response) {
      if (!response.ok) {
        throw new Error('Request failed with status: ' + response.status);
      }
      return response.json();
    })
    .catch(function (error) {
      throw error;
    });
}

function getQuote(quote) {
  httpGet(quote.url)
    .then(function (response) {
      document.getElementById(quote.text).textContent = response.quoteText;
      document.getElementById(quote.seasonEp).textContent =
        'Season: ' + response.quoteSeason + ' Episode: ' + response.quoteEp;
      quote.quoteId = response.id;
      getCharacter(response.characterID, quote.author);
    })
    .catch(function (error) {
      console.error('Error:', error);
    });
}

function getCharacter(id, author) {
  let url =
    'https://tteapi-4-env.eba-7w3sxei8.af-south-1.elasticbeanstalk.com/api/characters/' +
    id;
  httpGet(url)
    .then(function (response) {
      document.getElementById(author).textContent = 'Author: ' + response.name;
    })
    .catch(function (error) {
      console.error('Error:', error);
    });
}

function favoriteQuoteOfTheDay() {
  const data = { UserID: userId, QuoteID: quoteOfTheDay.QuoteID };
  const url =
    'https://tteapi-4-env.eba-7w3sxei8.af-south-1.elasticbeanstalk.com/api/favourites';
  fetch(url + '?' + queryParams, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  }).then((response) => {
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
  });
}

function favoriteQuoteOfTheWeek() {
  const data = { UserID: userId, QuoteID: quoteOfTheWeek.QuoteID };
  const url =
    'https://tteapi-4-env.eba-7w3sxei8.af-south-1.elasticbeanstalk.com/api/favourites';
  fetch(url + '?' + queryParams, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  }).then((response) => {
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
  });
}

function favoriteQuoteOfTheMonth() {
  const data = { UserID: userId, QuoteID: quoteOfTheMonth.QuoteID };
  const url =
    'https://tteapi-4-env.eba-7w3sxei8.af-south-1.elasticbeanstalk.com/api/favourites';
  fetch(url + '?' + queryParams, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  }).then((response) => {
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
  });
}

// window.addEventListener('load', async () => {
//   const token = localStorage.getItem('token');

//   if (!token) {
//     window.location.href = '../index.html';
//   }
// });
