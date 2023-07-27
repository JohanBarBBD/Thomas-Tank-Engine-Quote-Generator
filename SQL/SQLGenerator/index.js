const fs = require('fs');

const SQL = [];
const Lines = fs.readFileSync('Lines.txt','utf-8')
const season = 1;
const ep = 1;

Lines.split('\n').map(line=>{
  if (!line || !line.includes(':'))
    return;

  if (line.includes('.'))
  {
    const char = line.split(':')[0].trim();
    const quotes = line.split(':')[1].trim().split('.');
    quotes.map((subLine)=>{
      subLine = subLine.trim();
      if (!subLine)
        return;
      const quote = subLine
      SQL.push(`EXEC insertQuoteWithCharacterName @Quote = '${quote}',  @CharName = '${char}', @EPNum = ${ep}, @SeasonNum = ${season};`)
    })
  }
  else
  {
    const char = line.split(':')[0].trim();
    const quote = line.split(':')[1].trim();
    SQL.push(`EXEC insertQuoteWithCharacterName @Quote = '${quote}',  @CharName = '${char}', @EPNum = ${ep}, @SeasonNum = ${season};`)

  }
})

fs.writeFileSync('SQLOut.txt',SQL.join('\n'))
