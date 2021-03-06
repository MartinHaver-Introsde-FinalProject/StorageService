package resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONObject;
import model.Activity;
import model.ActivitySelection;
import model.Goal;
import model.HealthMeasure;
import model.HealthMeasureHistory;
import model.Person;

@Stateless
@LocalBean
@Path("/storage")
public class StorageResource {

	private static URI getEx1BaseURI() {
		return UriBuilder.fromUri("https://warm-gorge-12466.herokuapp.com/database").build();
	}

	//Getting a motivation quote from external service Forismatic.com
	 @GET
     @Path("/getQuote")
     public Response getQuote2() throws ClientProtocolException, IOException {
        
        String ENDPOINT = "https://gentle-anchorage-46419.herokuapp.com/adapter/getQuote";

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(ENDPOINT);
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        JSONObject o = new JSONObject(result.toString());
        if(response.getStatusLine().getStatusCode() == 200){
            return Response.ok(o.toString()).build();
         }
        return Response.status(204).build();
     }
	
	//Info about the service.
	@GET
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String getInfo() {
		return "This storage service is part of final project by M.Haver.";
	}

	//Getting list of people existing in database.
	@GET
	@Path("/person")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Person> getAll() {
		System.out.println("Getting list of people...");
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getEx1BaseURI()).path("person");
		Response response = service.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
		int httpStatus = response.getStatus();
		if (httpStatus == 200) {
			List<Person> people = response.readEntity(new GenericType<List<Person>>() {
			});
			return people;
		}
		return null;
	}

	//Getting the detail information of a Person identified by idPerson.
	@GET
	@Path("/person/{idPerson}")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person getPersonById(@PathParam("idPerson") int idPerson) {
		System.out.println("Getting Person with idPerson = " + idPerson + "...");
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getEx1BaseURI()).path("person").path(String.valueOf(idPerson));
		Response response = service.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
		int httpStatus = response.getStatus();
		if (httpStatus == 200) {
			Person person = response.readEntity(Person.class);
			return person;
		}
		return null;
	}

	//Getting the current Goal of a Person identified by idPerson.
	@GET
	@Path("person/{idPerson}/goal")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Goal getCurrentGoalOfPersonById(@PathParam("idPerson") int idPerson) {
		System.out.println("Getting current Person's Goal who is identified by idPerson = " + idPerson + "...");
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getEx1BaseURI()).path("person").path(String.valueOf(idPerson)).path("goal");
		Response response = service.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
		int httpStatus = response.getStatus();
		if (httpStatus == 200) {
			Goal goal = response.readEntity(Goal.class);
			return goal;
		}
		return null;
	}

	//Getting the list of the Goal histories of a Person identified by idPerson
	@GET
	@Path("/person/{idPerson}/goalHistory")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Goal> getGoalHistoryOfPersonById(@PathParam("idPerson") int idPerson) {
		System.out.println("Getting Person's Goal history who is identified by idPerson = " + idPerson + "...");
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getEx1BaseURI()).path("person").path(String.valueOf(idPerson))
				.path("goalHistory");
		Response response = service.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
		int httpStatus = response.getStatus();
		if (httpStatus == 200) {
			List<Goal> goals = response.readEntity(new GenericType<List<Goal>>() {
			});
			return goals;
		}
		return null;
	}

	//Getting the list of Health Measures of a Person identified by idPerson.
	@GET
	@Path("/person/{idPerson}/healthMeasure")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<HealthMeasure> getHealthMeasureOfPersonById(@PathParam("idPerson") int idPerson) {
		System.out.println("Getting Person's Health Measure who is identified by idPerson = " + idPerson + "...");
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getEx1BaseURI()).path("person").path(String.valueOf(idPerson))
				.path("healthMeasure");
		Response response = service.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
		int httpStatus = response.getStatus();
		if (httpStatus == 200) {
			List<HealthMeasure> healthMeasures = response.readEntity(new GenericType<List<HealthMeasure>>() {
			});
			return healthMeasures;
		}
		return null;
	}

	//Getting the list of Health Measure Histories of a Person identified by idPerson.
	@GET
	@Path("/person/{idPerson}/healthMeasureHistory")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<HealthMeasureHistory> getHealthMeasureHistoryOfPersonById(@PathParam("idPerson") int idPerson) {
		System.out
				.println("Getting Person's Health Measure History who is identified by idPerson = " + idPerson + "...");
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getEx1BaseURI()).path("person").path(String.valueOf(idPerson))
				.path("healthMeasureHistory");
		Response response = service.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
		int httpStatus = response.getStatus();
		if (httpStatus == 200) {
			List<HealthMeasureHistory> healthMeasureHistories = response
					.readEntity(new GenericType<List<HealthMeasureHistory>>() {
					});
			return healthMeasureHistories;
		}
		return null;
	}

	//Getting the current Activity Selection of current Goal of a Person identified by idPerson.
	@GET
	@Path("/person/{idPerson}/goal/activitySelection")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ActivitySelection getActivitySelectionOfCurrentGoalOfPersonById(@PathParam("idPerson") int idPerson) {
		System.out
				.println("Getting the current Activity Selection of current Goal of a Person identified by idPerson = "
						+ idPerson + "...");
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getEx1BaseURI()).path("person").path(String.valueOf(idPerson))
				.path("/goal/activitySelection");
		Response response = service.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
		int httpStatus = response.getStatus();
		if (httpStatus == 200) {
			ActivitySelection activitySelection = response.readEntity(ActivitySelection.class);
			return activitySelection;
		}
		return null;
	}

	//Getting the list of activities existing in database.
	@GET
	@Path("/activity")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Activity> getActivities(@Context UriInfo ui) {
		MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
		String type = queryParams.getFirst("type");
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getEx1BaseURI()).path("activity").queryParam("activityType", type);
		Response response = service.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
		int httpStatus = response.getStatus();
		if (httpStatus == 200) {
			List<Activity> activities = response.readEntity(new GenericType<List<Activity>>() {
			});
			return activities;
		}
		return null;
	}

	//Getting the detail information of a activity identified by idActivity.
	@GET
	@Path("/activity/{idActivity}")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Activity getActivity(@PathParam("idActivity") int idActivity) {
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getEx1BaseURI()).path("activity").path(String.valueOf(idActivity));
		Response response = service.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
		int httpStatus = response.getStatus();
		if (httpStatus == 200) {
			Activity activity = response.readEntity(Activity.class);
			return activity;
		}
		return null;
	}

	//Creating new Goal without any Food Selection and Activity Selection for a Person identified by idPerson
	@POST
	@Path("/person/{idPerson}/goal")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person saveGoalForPerson(@PathParam("idPerson") int idPerson, Goal goal) throws IOException {
		System.out.println(
				"Creating a new Goal with empty Food Selection and Activity Selection for Person who is identified by idPerson = "
						+ idPerson + "...");
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getEx1BaseURI()).path("person").path(String.valueOf(idPerson)).path("goal");
		Response response = service.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(goal));
		int httpStatus = response.getStatus();
		if (httpStatus == 200) {
			Person person = response.readEntity(Person.class);
			return person;
		}
		return null;
	}

	//Creating new Health Measure belonging to a Health Measure Type and putting the old Health Measure into Health Measure History for a Person identified by idPerson.
	@POST
	@Path("/person/{idPerson}/healthMeasure/{healthMeasureType}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person saveHealthMeasureForPerson(@PathParam("idPerson") int idPerson,
			@PathParam("healthMeasureType") String healthMeasureType, HealthMeasure healthMeasure) throws IOException {
		System.out.println("Creating new Health Measure for person with id = " + idPerson
				+ " and with Health Measure Type = " + healthMeasureType + "...");
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getEx1BaseURI()).path("person").path(String.valueOf(idPerson))
				.path("healthMeasure").path(healthMeasureType);
		Response response = service.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(healthMeasure));
		int httpStatus = response.getStatus();
		if (httpStatus == 200) {
			Person person = response.readEntity(Person.class);
			return person;
		}
		return null;
	}

	//Creating new Activity Selection for current Goal of a Person
	@POST
	@Path("/person/{idPerson}/goal/activitySelection")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person saveGoalForPerson(@PathParam("idPerson") int idPerson, Activity activity) throws IOException {
		System.out.println("Creating new Activity Selection for current Goal of a Person with id = " + idPerson
				+ " and with activity id = " + activity.getIdActivity() + "...");
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getEx1BaseURI()).path("person").path(String.valueOf(idPerson)).path("goal")
				.path("activitySelection");
		Response response = service.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(activity));
		int httpStatus = response.getStatus();
		if (httpStatus == 200) {
			Person person = response.readEntity(Person.class);
			return person;
		}
		return null;
	}

	//Updating Activity Selection (time, usedCalories)
	@PUT
	@Path("/person/{idPerson}/goal/activitySelection")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person saveGoalForPerson(@PathParam("idPerson") int idPerson, ActivitySelection activitySelection)
			throws IOException {
		System.out.println("Updating a current Activity Selection of a current Goal of a Person with id = " + idPerson
				+ " and with Activity Selection id = " + activitySelection.getIdActivitySelection() + "...");
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getEx1BaseURI()).path("person").path(String.valueOf(idPerson)).path("goal")
				.path("activitySelection");
		Response response = service.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.put(Entity.json(activitySelection));
		int httpStatus = response.getStatus();
		if (httpStatus == 200) {
			Person person = response.readEntity(Person.class);
			return person;
		}
		return null;
	}

	//Updating the Goal.
	@PUT
	@Path("/person/{idPerson}/goal")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person updateGoalForPerson(@PathParam("idPerson") int idPerson, Goal goal) throws IOException {
		System.out.println("Updating a current Goal of a Person with id = " + idPerson);
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getEx1BaseURI()).path("person").path(String.valueOf(idPerson)).path("goal");
		Response response = service.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.put(Entity.json(goal));
		int httpStatus = response.getStatus();
		if (httpStatus == 200) {
			Person person = response.readEntity(Person.class);
			return person;
		}
		return null;
	}
}
