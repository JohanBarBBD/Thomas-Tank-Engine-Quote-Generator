const queryParams = new URLSearchParams({
  jwt: 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMDcwODI5NzA2MTQ2ODQ1NjYwMzMiLCJlbWFpbCI6Im1hdHRoZXcuZGFjcmUxQGdtYWlsLmNvbSIsIm5hbWUiOiJNYXR0aGV3IERhY3JlIiwiaWF0IjoxNjkwNDcwNjA2LCJleHAiOjE2OTA0NzQyMDZ9.Kn-4oSsp-NBhMLg2TDU51cSLnJg33k7UhsRc1TmGIxo',
});

quoteOfTheDay = {
  url: 'http://tteapi-4-env.eba-7w3sxei8.af-south-1.elasticbeanstalk.com/api/quotes/quote-of-the-day',
  text: 'quoteOfTheDayText',
  author: 'quoteOfTheDayAuthor',
  seasonEp: 'quoteOfTheDaySeasonEp',
  quoteId: undefined,
};

quoteOfTheWeek = {
  url: 'http://tteapi-4-env.eba-7w3sxei8.af-south-1.elasticbeanstalk.com/api/quotes/quote-of-the-week',
  text: 'quoteOfTheWeekText',
  author: 'quoteOfTheWeekAuthor',
  seasonEp: 'quoteOfTheWeekSeasonEp',
  quoteId: undefined,
};

quoteOfTheMonth = {
  url: 'http://tteapi-4-env.eba-7w3sxei8.af-south-1.elasticbeanstalk.com/api/quotes/quote-of-the-month',
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
      console.log(response);
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
    'http://tteapi-4-env.eba-7w3sxei8.af-south-1.elasticbeanstalk.com/api/characters/' +
    id;
  httpGet(url)
    .then(function (response) {
      document.getElementById(author).textContent = 'Author: ' + response.name;
    })
    .catch(function (error) {
      console.error('Error:', error);
    });
}
