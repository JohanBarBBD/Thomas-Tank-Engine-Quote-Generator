const queryParams = new URLSearchParams({
  jwt: 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMDcwODI5NzA2MTQ2ODQ1NjYwMzMiLCJlbWFpbCI6Im1hdHRoZXcuZGFjcmUxQGdtYWlsLmNvbSIsIm5hbWUiOiJNYXR0aGV3IERhY3JlIiwiaWF0IjoxNjkwNDcwNjA2LCJleHAiOjE2OTA0NzQyMDZ9.Kn-4oSsp-NBhMLg2TDU51cSLnJg33k7UhsRc1TmGIxo',
});

const url =
  'http://tteapi-4-env.eba-7w3sxei8.af-south-1.elasticbeanstalk.com/api/quote/image';

handlePNGImage(url);

function handlePNGImage(url) {
  // Replace with the actual URL of the PNG image
  fetch(url)
    .then((response) => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.blob(); // Get the response data as a Blob
    })
    .then((blob) => {
      // Create a local URL for the Blob
      const imageUrl = URL.createObjectURL(blob);

      // Set the src attribute of the <img> element to display the image
      document.getElementById('imageElement').src = imageUrl;
    })
    .catch((error) => {
      console.error('Error fetching the image:', error);
    });
}
