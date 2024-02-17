package dao;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.List;

public class MongoManagement {
    private MongoClient mongoClient = null;

    public MongoManagement() {
        mongoClient = MongoDB.getClient();
    }
    
    
    /** Metodo para mostrar buscar todos los patients de orden asc o desc
     * 
     * @param databaseName nombre base de datos
     * @param collectionName nombre de la collection
     * @param field filtrar por (nombre o apellido)
     * @param order Asc or desc
     * @return lista ordenada de patient por (nombre o apellido) asc o desc
     */
    public String showAll(String databaseName, String collectionName, String field, int order) {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        
        JsonWriterSettings settings = JsonWriterSettings.builder().indent(true).build();

        FindIterable<Document> documents;
        if (order > 0) {
            documents = collection.find().sort(Sorts.ascending(field));
        } else {
            documents = collection.find().sort(Sorts.descending(field));
        }

        StringBuilder result = new StringBuilder();
        for (Document document : documents) {
            result.append(document.toJson(settings)).append("\n");
        }

        return result.toString();
    }
    

    /** Metodo para encontrar buscar un patient mediante ID, nombre o apellido de orden asc o desc
     * 
     * @param databaseName nombre base de datos
     * @param collectionName nombre de la collection
     * @param patientSearch segun (nombre o apellido)
     * @param field filtrar por (nombre o apellido)
     * @param order Asc or desc
     * @return lista ordenada de patient buscado por (ID, nombre o apellido) ordenado por (nombre o apellido) asc o desc
     */
    public List<Document> findPatients(String databaseName, String collectionName, String patientSearch, String field, int order) {
        List<Document> patients = new ArrayList<>();
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        // Buscar pacient segun (ID, nombre o apellido) y ordenar segun el parametro de orden
        FindIterable<Document> results = collection.find(new Document(field, patientSearch)).sort(new Document(field, order));
        for (Document doc : results) {
            patients.add(doc);
        }

        return patients;
    }
    
    /** Metodo para encontrar buscar un patient mediante ID, nombre o apellido
     * 
     * @param databaseName nombre base de datos
     * @param collectionName nombre de la collection
     * @param patientSearch segun el texto ingresado (ID, nombre o apellido)
     * @return patient con ID o patients con mismo nombre/apellido
     */
    public List<Document> findPatients(String databaseName, String collectionName, String patientSearch) {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        List<Document> patients = new ArrayList<>();

        // Intentar buscar por ID
        try {
//            ObjectId id = new ObjectId(patientSearch);
//            Document patientById = collection.find(eq("_id", id)).first();
        	
        	Document patientById = findById(databaseName, collectionName, patientSearch);
            if (patientById != null) {
                patients.add(patientById);
                return patients; // Devolver el paciente con el ID buscado
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        // Buscar pacientes por primer nombre
        FindIterable<Document> patientsByFirstName = collection.find(regex("first_name", "^" + patientSearch + "$", "i"));
        for (Document patient : patientsByFirstName) {
            patients.add(patient);
        }

        // Buscar pacientes por apellido
        FindIterable<Document> patientsByLastName = collection.find(regex("last_name", "^" + patientSearch + "$", "i"));
        for (Document patient : patientsByLastName) {
            if (!patients.contains(patient)) {
                patients.add(patient);
            }
        }

        return patients;
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

        Document filter = collection.find(eq("_id", objectId)).first();
        

        return filter;
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
