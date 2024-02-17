package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import dao.MongoDB;
import dao.MongoManagement;
import io.IO;

public class Menu {
	
	
	private static final String DATABASE = "hospital";
	private static final String COLLECTION = "patients";
	
	public static void main(String[] args) {
		
		MongoManagement management = new MongoManagement();
		
		
		int option;
		
		boolean loop = true;
		
		
		while (loop) {
			option = menu();
			
			switch (option) {
			case 1: {
				insertPatient(management);
				break;
			}
			
			case 2: {
				updatePersonal(management);
				break;
			}
			
			case 3: {
				updateMedical(management);
				break;
			}
			
			case 4: {
				delete(management);
				break;
			}
			
			case 5: {
				showAll(management);
				break;
			}
			
			case 6: {
				searchById(management);
				break;
			}
			
			case 7: {
				search(management);
				break;
			}
			
			case 8: {
				loop = false;
				break;
			}
			
			default:
				throw new IllegalArgumentException("Unexpected value: " + option);
			}
		}
		
	}
	
	private static void search(MongoManagement management) {
		IO.print("Enter patient ID, first name or last name: ");
		String searchID = IO.readString();
		
		JsonWriterSettings settings = JsonWriterSettings.builder().indent(true).build();
		
		 List<Document> results = management.findPatients(DATABASE, COLLECTION, searchID);
				
		 if (!results.isEmpty()) {
	            StringBuilder resultText = new StringBuilder();
	            for (Document doc : results) {
	                resultText.append(doc.toJson(settings)).append("\n\n"); 
	            }
	            IO.println(resultText.toString());
	        }
		
	}

	private static void searchById(MongoManagement management) {
		IO.print("Enter patient ID: ");
		String searchID = IO.readString();
		
		Document patient = management.findById(DATABASE, COLLECTION, searchID);
		
		JsonWriterSettings settings = JsonWriterSettings.builder().indent(true).build();
		
		IO.println(patient.toJson(settings));
		
	}

	private static void showAll(MongoManagement management) {
		IO.print(management.showAll(DATABASE, COLLECTION));
	}

	private static void delete(MongoManagement management) {
		IO.print("Enter patient ID: ");
		String searchID = IO.readString();
		
		boolean deleted = management.deletePatient(DATABASE, COLLECTION, searchID);
		
		IO.println(deleted ? "Deleted" : "Not deleted");
	}

	private static void updateMedical(MongoManagement management) {
		Document update = new Document();
		
		String bloodType;
		List<String> allergies = new ArrayList<String>();
		List<String> medications = new ArrayList<String>();
		List<String> medicalHistory = new ArrayList<String>();
		String[] bloods = new String[2];
		
		
		IO.print("Enter patient ID: ");
		String searchID = IO.readString();
		
		IO.print("Insurance number: {if patient in database, leave blank}");
		String insurance = IO.readString();
		
		IO.print("Enter blood type/s: {enter to exit}");
		for (int i = 0; i < bloods.length; i++) {
			bloodType = IO.readString();
			while(!bloodType.isEmpty()) {
				bloods = append(bloods, bloodType);
				bloodType = IO.readString();
			}
		}
		
		IO.println("Enter allergies: {enter to exit}");
		String allergy = IO.readString();
		
		while(!allergy.isEmpty()) {
			allergies.add(allergy);
			allergy = IO.readString();
		}
		
		IO.println("Enter medications: {enter to exit}");
		String medication = IO.readString();
		
		while(!medication.isEmpty()) {
			medications.add(medication);
			medication = IO.readString();
		}
		
		IO.println("Enter medical information: {enter to exit}");
		String medical = IO.readString();
		
		while(!medical.isEmpty()) {
			medicalHistory.add(medical);
			medical = IO.readString();
		}
		
		boolean updated = false;
		
		if (insurance.isEmpty()) {
			update.append("medical_information",
					new Document().append("blood_type", bloods)
							.append("allergies", allergies)
							.append("medications", medications)
							.append("medical_history", medicalHistory));

			updated = management.update(DATABASE, COLLECTION, searchID, update);
			
			IO.println(updated ? "Updated" : "Not updated");
		} else {
			update.append("medical_information",
					new Document().append("insurance", insurance)
							.append("blood_type", bloods)
							.append("allergies", allergies)
							.append("medications", medications)
							.append("medical_history", medicalHistory));
			updated = management.update(DATABASE, COLLECTION, searchID, update);
			
			IO.println(updated ? "Updated" : "Not updated");
		}
		
	}
	
	private static <T> T[] append(T[] arr, T element) {
	    final int N = arr.length;
	    arr = Arrays.copyOf(arr, N + 1);
	    arr[N] = element;
	    return arr;
	}

	private static void updatePersonal(MongoManagement management) {
		
		Document update = new Document();
		
		IO.print("Enter patient ID: ");
		String searchID = IO.readString();
		
		IO.println("First name: ");
		String firstName = IO.readString();
		
		IO.println("Last name: ");
		String lastName = IO.readString();
		

		IO.println("Address: ");
		String address = IO.readString();
		
		IO.println("Email address: ");
		String email = IO.readString();
		
		IO.println("Phone number: ");
		String phoneNumber = IO.readString();
		
		IO.println("Emergency contact name: ");
		String emergencyContactName = IO.readString();
		
		IO.println("Relation (Family / Friend / Other): ");
		String relation = IO.readString();
		
		
		IO.println("Emergency phone number: ");
		String emergencyPhone = IO.readString();
		IO.println("Emergency address: ");
		String emergencyAddress = IO.readString();
		
		update.append("first_name", firstName)
		.append("last_name", lastName)
		.append("address", address)
		.append("phone_number", phoneNumber)
		.append("email_address", email)
		.append("emergency_contact", new Document()
				.append("name", emergencyContactName)
				.append("relation", relation)
				.append("phone_number", emergencyPhone)
				.append("address", emergencyAddress));

		boolean updated = management.update(DATABASE, COLLECTION, searchID, update);
		
		IO.println(updated ? "Added" : "Not added");
	}

	public static int menu() {
		int option;
		
		IO.println("""
				1: Insert patient
				2: Update personal information
				3: Update Medical information
				4: Delete patient
				5: Show all patient
				6: Search patient by id
				7: Search patiend
				8: Exit
				""");
		
		option = IO.readInt();
		
		return option;
	}
	
	public static void insertPatient(MongoManagement management) {
		IO.println("First name: ");
		String firstName = IO.readString();
		
		IO.println("Last name: ");
		String lastName = IO.readString();
		
		
		IO.println("Date of birth: ");
		Date birthDate = IO.readDate();
		
		IO.println("Gender(M / F): ");
		String gender = IO.readString().toUpperCase();
		
		
		while (!gender.equals("F") && !gender.equals("M")) {
			gender = IO.readString().toUpperCase();
			IO.println("Choose a valid gender (M / F)");
		}
		
		IO.println("Address: ");
		String address = IO.readString();
		
		IO.println("Email address: ");
		String email = IO.readString();
		
		IO.println("Phone number: ");
		String phoneNumber = IO.readString();
		
		IO.println("Emergency contact name: ");
		String emergencyContactName = IO.readString();
		
		IO.println("Relation (Family / Friend / Other): ");
		String relation = IO.readString();
		
		
		IO.println("Emergency phone number: ");
		String emergencyPhone = IO.readString();
		IO.println("Emergency address: ");
		String emergencyAddress = IO.readString();
		
		Document patient = new Document()
				.append("first_name", firstName)
				.append("last_name", lastName)
				.append("date_of_birth", birthDate)
				.append("gender", gender)
				.append("address", address)
				.append("phone_number", phoneNumber)
				.append("email_address", email)
				.append("medical_information", new Document())
				.append("emergency_contact", new Document()
						.append("name", emergencyContactName)
						.append("relation", relation)
						.append("phone_number", emergencyPhone)
						.append("address", emergencyAddress));
		
		 boolean result = management.insert(DATABASE, COLLECTION, patient);
		 
		 IO.println(result ? "Added" : "Not added");
	}
	
	
}
