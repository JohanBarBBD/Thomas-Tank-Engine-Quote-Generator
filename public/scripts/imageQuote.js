handlePNGImage();

function handlePNGImage() {
  const url =
    'https://tteapi-4-env.eba-7w3sxei8.af-south-1.elasticbeanstalk.com/api/quote/image';
  fetch(url)
    .then((response) => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.blob();
    })
    .then((blob) => {
      const imageUrl = URL.createObjectURL(blob);

      document.getElementById('imageElement').src = imageUrl;
    })
    .catch((error) => {
      console.error('Error fetching the image:', error);
    });
}
