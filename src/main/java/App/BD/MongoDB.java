package App.BD;

import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

public class MongoDB {
    private static final String URL = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "gachaWorkshop";
    private static final String COLLECTION_NAME = "Articles";

    public static void addProduct(String nomProduit, String descriptionProduit, double prixProduit,
            MultipartFile image) {
        // Connectez-vous à la base de données MongoDB
        try (MongoClient mongoClient = MongoClients.create(URL)) {
            // Sélectionnez la base de données
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            // Sélectionnez la collection
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            // Convertissez l'image en base64
            String imageBase64 = encodeImageToBase64(image.getBytes());

            // Insérez le document avec l'image
            InsertOneResult result = collection.insertOne(new Document()
                    .append("nom", nomProduit)
                    .append("description", descriptionProduit)
                    .append("prix", prixProduit)
                    .append("image", imageBase64));

            // Affichez le résultat de l'opération d'insertion
            System.out.println("Résultat de l'opération d'insertion : " + result);

            System.out.println("Produit ajouté avec succès.");
        } catch (Exception e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }

    public static void addProductCSV(String nomProduit, String descriptionProduit, double prixProduit,
            File image) {
        // Connectez-vous à la base de données MongoDB
        try (MongoClient mongoClient = MongoClients.create(URL)) {
            // Sélectionnez la base de données
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            // Sélectionnez la collection
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            // Convertissez l'image en base64
            byte[] imageBytes = Files.readAllBytes(image.toPath());
            String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);

            // Insérez le document avec l'image
            InsertOneResult result = collection.insertOne(new Document()
                    .append("nom", nomProduit)
                    .append("description", descriptionProduit)
                    .append("prix", prixProduit)
                    .append("image", imageBase64));

            // Affichez le résultat de l'opération d'insertion
            System.out.println("Résultat de l'opération d'insertion : " + result);

            System.out.println("Produit ajouté avec succès.");
        } catch (Exception e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }

    // fonction pour convertir l'image en base64
    public static String encodeImageToBase64(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    // fonction pour la recherche d'article selon le nom ou le prix
    public static void searchProduct(String nomProduit, double prixProduit) {
        // Connectez-vous à la base de données MongoDB
        try (MongoClient mongoClient = MongoClients.create(URL)) {
            // Sélectionnez la base de données
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            // Sélectionnez la collection
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            // Recherchez les documents correspondants
            Document query = new Document();
            if (nomProduit != null && !nomProduit.isEmpty()) {
                query.append("nom", nomProduit);
            }
            if (prixProduit != 0) {
                query.append("prix", prixProduit);
            }

            // Affichez les documents correspondants
            collection.find(query).forEach(document -> System.out.println(document.toJson()));
        } catch (Exception e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }

    public static List<Document> getAllArticles() {
        List<Document> articles = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            // Sélectionnez la base de données
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            // Sélectionnez la collection
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            // Récupérez tous les documents dans la collection
            for (Document document : collection.find()) {
                articles.add(document);

            }

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des articles : " + e.getMessage());
        }

        return articles;
    }

    public static void deleteArticle(String articleId) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {

            System.out.println("Article ID : " + articleId);
            // Sélectionnez la base de données
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            // Sélectionnez la collection
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            // Supprimez l'article en utilisant son ID
            collection.deleteOne(new Document("_id", new ObjectId(articleId)));

            System.out.println("Article supprimé avec succès.");
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression de l'article : " + e.getMessage());
        }
    }

    public static List<Document> rechercheArticle(String rechercheTerm) {
        List<Document> Articles = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            // Sélectionnez la base de données
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            // Sélectionnez la collection
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            // Effectuez la recherche d'articles en fonction du terme spécifié
            Document query = new Document();
            query.append("$or", List.of(
                    new Document("nom", new Document("$regex", rechercheTerm).append("$options", "i"))));

            // Récupérez les documents filtrés dans la collection
            collection.find(query).forEach(Articles::add);

        } catch (Exception e) {
            System.err.println("Erreur lors de la recherche des articles : " + e.getMessage());
        }

        return Articles;
    }

    public static Document getArticleById(String articleId) {
        Document Article = new Document();
        System.err.println("Article ID : " + articleId);
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            // Sélectionnez la base de données
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            // Sélectionnez la collection
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            // Créez le filtre pour récupérer l'article par ID
            Document query = new Document("_id", new ObjectId(articleId));
            // Utilisez la méthode findOne pour récupérer un seul document
            Article = collection.find(query).first();

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de l'article par ID : " + e.getMessage());
        }
        return Article;
    }

}