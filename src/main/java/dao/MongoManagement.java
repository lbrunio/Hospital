package dao;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

public class MongoManagement {
    private MongoClient mongoClient = null;

    public MongoManagement() {
        mongoClient = MongoDB.getClient();
    }

    /** Metodo para encontrar un patient por su ID
     * 
     * @param databaseName nombre base de datos
     * @param collectionName nombre de la collection
     * @param patientID a buscar
     * @return patient con el ID proporcionado
     */
    public Document findById(String databaseName, String collectionName, String patientID) {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        ObjectId objectId;
        try {
            objectId = new ObjectId(patientID);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }

        Document filter = new Document("_id", objectId);
        

        return collection.find(filter).first();
    }

    /**  Metodo para actualizar un patient
     * 
     * @param databaseName nombre base de datos
     * @param collectionName nombre collection
     * @param patientID a actualizar
     * @param patient con los nuevos datos
     * @return true si la actualizacion fue exitosa
     */
    public boolean update(String databaseName, String collectionName, String patientID, Document patient) {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        ObjectId objectId = new ObjectId(patientID);
        Document filter = new Document("_id", objectId);

        Document updated = new Document("$set", patient);

        long modifiedCount = collection.updateOne(filter, updated).getModifiedCount();

        return modifiedCount > 0;
    }

    /** Metodo para insertar un nuevo documento
     * 
     * @param databaseName nombre base de datos
     * @param collectionName nombre de la collection
     * @param patient insertar a la base de datos
     * @return true si la insercion fue exitosa
     */
    public boolean insert(String databaseName, String collectionName, Document patient) {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        InsertOneResult result = collection.insertOne(patient);
        
        return result.wasAcknowledged();
    }

    /** Metodo para obtener todos los documentos de una colección
     * 
     * @param databaseName nombre base de datos
     * @param collectionName nombre de la collection
     * @return todos los patients que estan en la base de datos de formato json
     */
    public String showAll(String databaseName, String collectionName) {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        JsonWriterSettings settings = JsonWriterSettings.builder().indent(true).build();

        StringBuilder patients = new StringBuilder();

        for (String collectionNames : database.listCollectionNames()) {
            collection = database.getCollection(collectionNames);

            try (MongoCursor<Document> cursor = collection.find().iterator()) {
                while (cursor.hasNext()) {
                    Document document = cursor.next();
                    patients.append(document.toJson(settings)).append("\n");
                }
            }
            
        }
        
        return patients.toString();
    }

    /** Metodo para eliminar un paciente por su ID
     * 
     * @param databaseName nombre base de datos
     * @param collectionName nombre de la collection
     * @param patientId a borrar
     * @return true si se ha borrado correctamente
     */
    public boolean deletePatient(String databaseName, String collectionName, String patientId) {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        ObjectId objectId = new ObjectId(patientId);
        Document filter = new Document("_id", objectId);

        DeleteResult deleted = collection.deleteOne(filter);

        return deleted.getDeletedCount() > 0;
    }
}
