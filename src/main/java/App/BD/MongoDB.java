package App.BD;

import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class MongoDB {
    private static final String URL = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "gachaWorkshop";
    private static final String COLLECTION_NAME = "Articles";

    public static void addProduct(String nomProduit, String descriptionProduit, double prixProduit) {
        // Connectez-vous à la base de données MongoDB
        try (MongoClient mongoClient = MongoClients.create(URL)) {
            // Sélectionnez la base de données
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            // Sélectionnez la collection
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            InsertOneResult result = collection.insertOne(new Document()
                .append("nom", nomProduit)
                .append("description", descriptionProduit)
                .append("prix", prixProduit)
            );

            // Affichez le résultat de l'opération d'insertion
            System.out.println("Résultat de l'opération d'insertion : " + result);

            System.out.println("Produit ajouté avec succès.");
        } catch (Exception e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }
}