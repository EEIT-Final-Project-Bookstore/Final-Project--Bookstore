// ...existing code...
router.get('/search', (req, res) => {
  const { keyword, type } = req.query;
  let results = [];

  switch (type) {
    case 'title':
      results = books.filter(book => book.name.includes(keyword));
      break;
    case 'author':
      results = books.filter(book => book.author.includes(keyword));
      break;
    case 'category':
      results = books.filter(book => book.category.includes(keyword));
      break;
    case 'publisher':
      results = books.filter(book => book.publisher.includes(keyword));
      break;
    case 'subcategory':
      results = books.filter(book => book.subcategory.includes(keyword));
      break;
    default:
      results = [];
  }

  res.json(results);
});
// ...existing code...
