function extractTokenFromURL() {
    const urlParams = new URLSearchParams(location.search);
    const token = urlParams.get("Token");

    if (token) {
        localStorage.setItem("token", token);
        let decoded = parseJwt(token)
        localStorage.setItem("user",decoded.email)
    } else {
        console.log("Token not found in URL.");
    }
}
extractTokenFromURL()

window.addEventListener('load', async() =>{
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = '../index.html';
    }
});

function parseJwt (token) {
    let base64Url = token.split('.')[1];
    let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    let jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
    
}

const apiUrl = 'http://tteapi-4-env.eba-7w3sxei8.af-south-1.elasticbeanstalk.com'

async function getFavorites() {
    console.log(`${apiUrl}/api/favourites`);
    try{
      const response = await fetch(`${apiUrl}/api/favourites`)
        const myJson = await response.json();
        let list = document.getElementById('Favorites');
        for(let i =0; i< myJson.length; i++){
          let listItem = document.createElement('li');
          listItem.innerText=`${myJson[i]}`;
          list.appendChild(listItem);
        }
  
    }catch(err){  
      alert("Technical error try again later.")
    }
  
  }

getFavorites();
