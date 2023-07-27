const fs = require('fs');

const userCount = 19;
const quoteCount = 190;

function createNumberArr(size){
  const data = [];
  for (let i = 1; i < size;i++) {
    data.push(i);
  }
  return data;
}

function makeLenght(value, length){
  let strOut = `${value}`
  for (let i = strOut.length; i < length;i = strOut.length )
  {
    strOut = ` ${strOut}`
  }
  return strOut;
}

function generate(){
  const maxUserCount = 10;
  let sql = ''
  for (let i = 1; i < userCount; i++) {

    const numQuotesVal = Math.floor(Math.random() * maxUserCount)

    const availCodeIndex = createNumberArr(quoteCount);

    for (let x = 0; x < numQuotesVal; x++)
    {
      const randomQuoteIndex = Math.floor(Math.random() * availCodeIndex.length);
      const randomQuote = availCodeIndex.splice(randomQuoteIndex,1);

      sql += `EXEC insertFavourite @QuoteID = ${makeLenght(randomQuote,4)}, @UserID = ${makeLenght(i,4)};\n`
    }
  }

  fs.writeFileSync('FavInsertSQL.txt',sql,{encoding:'utf-8'})
}

generate();
