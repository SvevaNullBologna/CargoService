// Salvalo ad esempio nella cartella test/mongodb/populate_products.js
const { MongoClient } = require('mongodb');

async function main() {
  const uri = 'mongodb://localhost:27017';
  const client = new MongoClient(uri);

  try {
    await client.connect();
    console.log('Connesso a MongoDB');

    const db = client.db('cargodb');
    const collection = db.collection('products');

    // Svuota eventuali dati precedenti
    await collection.deleteMany({});
    console.log('Collezione products svuotata');

    // Inserisci documenti
    const documents = [
      { productId: 1,  jsonRep: JSON.stringify({ productId: 1,  name: 'p1',  weight: 100 }) }, //valido
      { productId: 2,  jsonRep: JSON.stringify({ productId: 2,  name: 'p2',  weight: 200 }) }, //valido
      { productId: 3,  jsonRep: JSON.stringify({ productId: 3,  name: 'p3',  weight: 1100 }) }, // troppo pesante
      { productId: 10, jsonRep: JSON.stringify({ productId: 10, name: 'p10', weight: 90 }) },   // valido
      { productId: 11, jsonRep: JSON.stringify({ productId: 10, name: 'p11', weight: 10 }) }, //valido
      { productId: 20, jsonRep: JSON.stringify({ productId: 20, name: 'p20', weight: 80 }) },   // slot occupato?
    ];

    const result = await collection.insertMany(documents);
    console.log(`Inseriti ${result.insertedCount} documenti`);
    Object.values(result.insertedIds).forEach(id => console.log('  - ID:', id));

  } catch (err) {
    console.error('Errore:', err);
  } finally {
    await client.close();
    console.log('Connessione chiusa');
  }
}

main();
