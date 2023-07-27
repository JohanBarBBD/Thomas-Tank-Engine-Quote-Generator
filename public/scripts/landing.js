function extractTokenFromURL() {
  const urlParams = new URLSearchParams(location.search);
  const token = urlParams.get("Token");

  if (token) {
    localStorage.setItem('token', token);
    let decoded = parseJwt(token);
    localStorage.setItem('user', decoded.email);
  } else {
    console.log('Token not found in URL.');
  }
}

extractTokenFromURL();

function parseJwt (token) {
    let base64Url = token.split('.')[1];
    let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    let jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
    
}

const apiUrl ='https://tteapi-4-env.eba-7w3sxei8.af-south-1.elasticbeanstalk.com';
const userQueryParam = new URLSearchParams({ email: localStorage.getItem('user')
})
const tokenQueryParam = new URLSearchParams({ jwt: localStorage.getItem('token')})

async function fetchQuoteDetails(quoteID) {
  const response = await fetch(`${apiUrl}/api/quotes/${quoteID}?${tokenQueryParam}`);
  const quoteData = await response.json();
  return quoteData;
}

async function getFavorites() {
  const userIdResponse = await fetch(
    `${apiUrl}/api/users/by-email?${userQueryParam}&${tokenQueryParam}`
  );
  const userIdJson = await userIdResponse.json();
  const userId = userIdJson.id;
  const favoritesResponse = await fetch(
      `${apiUrl}/api/favourites/${userId}?${tokenQueryParam}`
  );
  const myJson = await favoritesResponse.json();

  const quoteIDs = myJson.map(item => item.quoteID);
  const quoteDetails = await Promise.all(quoteIDs.map(fetchQuoteDetails));
  


  let list = document.getElementById('Favorites');
  for (let i = 0; i < quoteDetails.length; i++) {
    
    let listItem = document.createElement('li');
    listItem.innerText = `${quotequoteDetails[i].quoteText}`;
    list.appendChild(listItem);

    }
  }

getFavorites()

