let quoteOfTheDay = { quoteText: '', quoteEp: '', quoteSeason: '' };
let quoteOfTheWeek = { quoteText: '', quoteEp: '', quoteSeason: '' };
let quoteOfTheMonth = { quoteText: '', quoteEp: '', quoteSeason: '' };

document.getElementById('quoteOfTheDayText').textContent =
  quoteOfTheDay.quoteText;

function httpGet(url) {
  return fetch(url)
    .then(function (response) {
      if (!response.ok) {
        throw new Error('Request failed with status: ' + response.status);
      }
      return response.text();
    })
    .catch(function (error) {
      throw error;
    });
}

var url =
  'http://tteapi-4-env.eba-7w3sxei8.af-south-1.elasticbeanstalk.com/api/quotes/quote-of-the-day';
httpGet(url)
  .then(function (response) {
    console.log('Response:', response);
  })
  .catch(function (error) {
    console.error('Error:', error);
  });
