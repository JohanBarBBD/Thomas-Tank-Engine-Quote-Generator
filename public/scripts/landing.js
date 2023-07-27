const apiUrl = 'http://tteapi-4-env.eba-7w3sxei8.af-south-1.elasticbeanstalk.com'

async function getFavorites() {
    console.log(`${apiUrl}/api/favourites`);
    try{
      const response = await fetch(`${apiUrl}/api/favourites`)
        const myJson = await response.json();
        let list = document.getElementById('Favorites');
        for(let i =0; i< myJson.length && i<10; i++){
          let listItem = document.createElement('li');
          listItem.innerText=`${myJson[i]?.}`;
          list.appendChild(listItem);
        }
  
    }catch(err){  
      alert("Technical error try again later.")
    }
  
  }

getFavorites();
