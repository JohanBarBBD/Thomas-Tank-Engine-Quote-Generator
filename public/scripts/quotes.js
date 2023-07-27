const token = localStorage.getItem('token');
const userEmail = localStorage.getItem('user');
const queryParams = new URLSearchParams({
  jwt: token,
  email: userEmail,
});
let userId;

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

getUserId();
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
  const data = { userID: userId, quoteID: quoteOfTheDay.quoteId, "dateFavourited": "2023-07-27T19:52:05.605Z" };
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
      document.getElementById("dayQuote").style.backgroundColor = "#550000"

      throw new Error('Network response was not ok');
    }
    document.getElementById("dayQuote").style.backgroundColor = "#FFD700"
  });
}

function favoriteQuoteOfTheWeek() {
  const data = { userID: userId, quoteID: quoteOfTheWeek.quoteId, "dateFavourited": "2023-07-27T19:52:05.605Z" };
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
      document.getElementById("weekQuote").style.backgroundColor = "#550000"

      throw new Error('Network response was not ok');
    }
    document.getElementById("weekQuote").style.backgroundColor = "#FFD700"
  });
}

function favoriteQuoteOfTheMonth() {
  const data = { userID: userId, quoteID: quoteOfTheMonth.quoteId, "dateFavourited": "2023-07-27T19:52:05.605Z" };
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
      document.getElementById("monthQuote").style.backgroundColor = "#550000"

      throw new Error('Network response was not ok');
    }
    document.getElementById("monthQuote").style.backgroundColor ="#FFD700"
  });
}

function getUserId() {
  httpGet(
    'https://tteapi-4-env.eba-7w3sxei8.af-south-1.elasticbeanstalk.com/api/users/by-email'
  )
    .then(function (response) {
      userId = response.id;
    })
    .catch(function (error) {
      console.error('Error:', error);
    });
}

// window.addEventListener('load', async () => {
//   const token = localStorage.getItem('token');

//   if (!token) {
//     window.location.href = '../index.html';
//   }
// });
