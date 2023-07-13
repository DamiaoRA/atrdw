package atrdw.foursquare.analytics.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import atrdw.util.TimeQ;

public class QueryATrDWMainTest {

	private static Connection con;
	private static Statement st;
	private static String schema = "atrdw";

	public static void main(String[] args) throws SQLException {
		try {
			initConnection("localhost", "5432", "postgres", "postgres");
			List<TimeQ> times = new ArrayList<TimeQ>();

			System.out.println("Queries results:");

			times.add(queryQ1());
			times.add(queryQ2());
			times.add(queryQ3());
			times.add(queryQ4());
			times.add(queryQ5());
			times.add(queryQ6());
			times.add(queryQ7());
			times.add(queryQ8());
			times.add(queryQ9());
			times.add(queryQ10());
			times.add(queryQ11());
			times.add(queryQ12());

			System.out.println("\nTime (ms) spent on each query:");
			for (TimeQ t : times) {
				System.out.println(t.toString());
			}
		} finally {
			if (con != null)
				con.close();
		}
	}

	public static void initConnection(String url1, String port, String user, String pass) throws SQLException {
		String url = "jdbc:postgresql://" + url1 + ":" + port + "/trajetoria";
		Properties props = new Properties();
		props.setProperty("user", user);
		props.setProperty("password", pass);
		con = DriverManager.getConnection(url, props);
		con.setSchema(schema);
		st = con.createStatement();
	}

	// --1 What was the average distance traveled by people who used public
	// transport to go to a school?
	private static TimeQ queryQ1() throws SQLException {
		long t1 = System.currentTimeMillis();
		String sql = "WITH stopTransport AS (" + "  select f.num_trajectory, f.position, f.id_user"
				+ "  from fato f, tb_poi dimPoi" + "  where f.id_poi = dimPoi.id"
				+ "  		and dimPoi.category = 'Travel Transport' " + "  		and dimPoi.name ~* '(bus|subway)'"
				+ " )" + " select SUM(f.total_distance)/count(distinct f.id_user)"
				+ " from fato f, tb_poi dimPoi, stopTransport st" + " where f.id_poi = dimPoi.id "
				+ "	and dimPoi.category = 'school'" + "	and f.id_user = st.id_user"
				+ "	and f.num_trajectory = st.num_trajectory" + "	and f.position = st.position+1";

		ResultSet rs = st.executeQuery(sql);
		long count = 0;
		while (rs.next()) {
			System.out.println(
					"\nQ1: What was the average distance traveled by people who used public transport to go to a school?\nResult: "
							+ rs.getString(1));
			count++;
		}

		long t2 = System.currentTimeMillis();
		TimeQ time = new TimeQ(t1, t2, "Q1", count);
		return time;
	}

//	--2 What was the average distance traveled by people who used public transportation to visit the City College of New York?
	private static TimeQ queryQ2() throws SQLException {
		long t1 = System.currentTimeMillis();

		String sql = "WITH stopTransport AS (" + "  select f.num_trajectory, f.position, f.id_user"
				+ "  from fato f, tb_poi dimPoi" + "  where f.id_poi = dimPoi.id"
				+ "  		and dimPoi.category = 'Travel Transport' " + "  		and dimPoi.name ~* '(bus|subway)'"
				+ " )" + " select SUM(f.total_distance)/count(distinct f.id_user)"
				+ " from fato f, tb_poi dimPoi, stopTransport st" + " where f.id_poi = dimPoi.id "
				+ "	and dimPoi.name = 'The City College of New York'" + "	and f.id_user = st.id_user"
				+ "	and f.num_trajectory = st.num_trajectory" + "	and f.position = st.position+1";

		ResultSet rs = st.executeQuery(sql);
		long count = 0;
		while (rs.next()) {
			System.out.println(
					"\nQ2: What was the average distance traveled by people who used public transportation to visit the City College of New York?"
					 + "\nResult:" + rs.getString(1));
			count++;
		}

		long t2 = System.currentTimeMillis();
		TimeQ time = new TimeQ(t1, t2, "Q2", count);
		return time;
	}

//	--3 How many trajectories are there where the average speed is greater than 25 mph in rainy weather in New York City?
	private static TimeQ queryQ3() throws SQLException {
		long t1 = System.currentTimeMillis();

		String sql = "select count(distinct f.num_trajectory) " + "from fato f, tb_aspect asp, tb_poi poi "
				+ "where f.id_aspect = asp.id and f.id_poi = poi.id " + "	and f.\"position\" > 1 "
				+ "	and asp.value like 'Rain,%' " + "	and poi.city = 'New York' " + "	and f.duration > 0 "
				+ "	and ((f.distance)/(f.duration) > 25)";

		ResultSet rs = st.executeQuery(sql);
		long count = 0;
		while (rs.next()) {
			System.out.println("\nQ3: How many trajectories are there where the average speed is greater than 25 mph in rainy weather in New York City?" 
			     + "\nResult: " + rs.getString(1));
			count++;
		}

		long t2 = System.currentTimeMillis();
		TimeQ time = new TimeQ(t1, t2, "Q3", count);
		return time;
	}

//	--4 How many users traveled with an average speed greater than 25 mph in wet weather in New York State?
	private static TimeQ queryQ4() throws SQLException {
		long t1 = System.currentTimeMillis();

		String sql = "select count(distinct f.id_user) " + "from fato f, tb_aspect asp, tb_poi poi "
				+ "where f.id_aspect = asp.id and f.id_poi = poi.id " + "	and f.\"position\" > 1 "
				+ "	and asp.value like 'Rain,%' " + "	and poi.city = 'New York' " + "	and f.duration > 0 "
				+ "	and ((f.distance)/(f.duration) > 25)";

		ResultSet rs = st.executeQuery(sql);
		long count = 0;
		while (rs.next()) {
			System.out.println("\nQ4: How many users traveled with an average speed greater than 25 mph in wet weather in New York State?" 
					+"\nResult:" + rs.getString(1));
			count++;
		}

		long t2 = System.currentTimeMillis();
		TimeQ time = new TimeQ(t1, t2, "Q4", count);
		return time;
	}

//	--5 What is the total distance traveled by all users in New York City during 2012 who have made at least one restaurant stop?
	private static TimeQ queryQ5() throws SQLException {
		long t1 = System.currentTimeMillis();

		String sql = "WITH oneStop AS (" + "  select num_trajectory, f.id_user, min(f.position) as position"
				+ "  from fato f, tb_poi dimPoi, tb_time dimTime"
				+ "  where f.id_poi = dimPoi.id and f.id_time = dimTime.id"
				+ "  		and dimPoi.category = 'restaurant'" + "  		and dimTime.year = 2012"
				+ "  group by num_trajectory, f.id_user" + " )" + " select sum(f.distance) as distance_ny"
				+ " from fato f, tb_poi dimPoi, tb_time dimTime, oneStop"
				+ " where f.id_poi = dimPoi.id and dimtime.id = f.id_time " + "	and dimPoi.city = 'New York'"
				+ "	and dimTime.year = 2012" + "	and f.num_trajectory = oneStop.num_trajectory"
				+ "	and f.id_user = oneStop.id_user" + "	and f.position > oneStop.position";
		ResultSet rs = st.executeQuery(sql);
		long count = 0;
		while (rs.next()) {
			System.out.println("\nQ5: What is the total distance traveled by all users in New York City during 2012 who have made at least one restaurant stop?" 
					+ "\nResult: " + rs.getString(1));
			count++;
		}

		long t2 = System.currentTimeMillis();
		TimeQ time = new TimeQ(t1, t2, "Q5", count);
		return time;
	}

//	--6 What is the total distance traveled by all users in New York City during the 2012 semesters and who have at least one stop at Liberty State Park?
	private static TimeQ queryQ6() throws SQLException {
		long t1 = System.currentTimeMillis();

		String sql = "WITH oneStop AS (" + "  select num_trajectory, f.id_user, min(f.position) as position"
				+ "  from fato f, tb_poi dimPoi, tb_time dimTime"
				+ "  where f.id_poi = dimPoi.id and f.id_time = dimTime.id"
				+ "  		and dimPoi.name ~* 'Liberty State Park'" + "  group by num_trajectory, f.id_user" + " )"
				+ " select sum(f.distance) as distance_ny, dimTime.semester"
				+ " from fato f, tb_poi poi, tb_time dimTime, oneStop"
				+ " where f.id_poi = poi.id and dimtime.id = f.id_time" + "	and poi.city = 'New York'"
				+ "	and dimTime.year = 2012" + "	and f.num_trajectory = oneStop.num_trajectory"
				+ "	and f.id_user = oneStop.id_user" + " group by dimTime.semester";
		ResultSet rs = st.executeQuery(sql);
		long count = 0;
		while (rs.next()) {
			System.out.println("\nQ6: What is the total distance traveled by all users in New York City during the 2012 semesters and who have at least one stop at Liberty State Park?" 
					+ "\nResult: " +rs.getString(1));
			count++;
		}

		long t2 = System.currentTimeMillis();
		TimeQ time = new TimeQ(t1, t2, "Q7", count);
		return time;
	}

	// --7 What is the average speed of users when they are driving from home to a mall?
	private static TimeQ queryQ7() throws SQLException {
		String sql = "WITH stopResidence AS ( " + "  select num_trajectory, f.position, f.id_user "
				+ "  from fato f, tb_poi dimPoi " + "  where f.id_poi = dimPoi.id "
				+ "  		and dimPoi.category = 'Residence'  " + ") "
				+ "select SUM(f.distance)/SUM(f.duration) as speed  " + "from fato f, tb_poi dimPoi, stopResidence r "
				+ "where f.id_poi = dimPoi.id  " + "	and dimPoi.category = 'mall' " + "	and f.id_user = r.id_user "
				+ "	and f.num_trajectory = r.num_trajectory " + "	and f.position = r.position+1";

		long t1 = System.currentTimeMillis();
		ResultSet rs = st.executeQuery(sql);
		long count = 0;
		while (rs.next()) {
			System.out.println("\nQ7: What is the average speed of users when they are driving from home to a mall?" 
					+ "\nResult: " + rs.getString(1));
			count++;
		}

		long t2 = System.currentTimeMillis();
		TimeQ time = new TimeQ(t1, t2, "Q7", count);
		return time;
	}

//	--8 On average, how long does it take a person to leave an entertainment and visit a highly rated restaurant?
	private static TimeQ queryQ8() throws SQLException {
		long t1 = System.currentTimeMillis();

		String sql = "WITH oneStop AS (" + "  select num_trajectory, f.id_user, f.position"
				+ "  from fato f, tb_poi dimPoi" + "  where f.id_poi = dimPoi.id"
				+ "  		and dimPoi.category = 'Entertainment'" + "  group by num_trajectory, f.id_user, f.position"
				+ " )" + " select  sum(f.duration)/count(f.id_user)"
				+ " from fato f, tb_poi dimPoi, tb_aspect dimAspect, oneStop"
				+ " where f.id_poi = dimPoi.id and dimAspect.id = f.id_aspect " + "	and dimPoi.category = 'restaurant'"
				+ "	and f.num_trajectory = oneStop.num_trajectory" + "	and f.id_user = oneStop.id_user"
				+ "	and f.position = oneStop.position+1"
				+ "	and (substring(dimAspect.value from ',([+-]?([0-9]*[.])?[0-9]+),')::numeric) > 6";
		ResultSet rs = st.executeQuery(sql);
		long count = 0;
		while (rs.next()) {
			System.out.println("\nQ8: On average, how long does it take a person to leave an entertainment and visit a highly rated restaurant?" 
					+ "\nResult: " + rs.getString(1));
			count++;
		}

		long t2 = System.currentTimeMillis();
		TimeQ time = new TimeQ(t1, t2, "Q8", count);
		return time;
	}

//	--9 Approximately, what is the total distance traveled by people within the State of New Jersey in the year 2012, satisfying the Home - Work - Entertainment moving pattern, not necessarily consecutive, lasting at least 4 hours?
	private static TimeQ queryQ9() throws SQLException {
		long t1 = System.currentTimeMillis();

		String sql = "WITH stopResidence AS (" + "  select num_trajectory, f.id_user, f.position"
				+ "  from fato f, tb_poi dimPoi" + "  where f.id_poi = dimPoi.id"
				+ "  		and dimPoi.category = 'Residence'" + "		and dimPoi.state = 'New Jersey'"
				+ "  group by num_trajectory, f.id_user, f.position" + " )," + " stopWork AS ("
				+ "  select num_trajectory, f.id_user, f.position" + "  from fato f, tb_poi dimPoi"
				+ "  where f.id_poi = dimPoi.id" + "  		and dimPoi.category = 'work'"
				+ "		and dimPoi.state = 'New Jersey'" + "  group by num_trajectory, f.id_user, f.position" + " )"
				+ " select SUM(f.distance)" + " from fato f, tb_poi dimPoi, tb_time dimTime, stopResidence, stopWork"
				+ " where f.id_poi = dimPoi.id and f.id_time = dimtime.id" + "	and dimPoi.state = 'New Jersey'"
				+ "	and dimTime.\"year\" = 2012" + "	and dimPoi.category = 'Entertainment'"
				+ "	and f.num_trajectory=stopResidence.num_trajectory"
				+ "	and f.num_trajectory=stopWork.num_trajectory" + "	and f.id_user = stopResidence.id_user"
				+ "	and f.id_user = stopWork.id_user" + "	and f.position > stopWork.position"
				+ "	and stopWork.position > stopResidence.position" + "	and f.total_duration >= 4";

		ResultSet rs = st.executeQuery(sql);
		long count = 0;
		while (rs.next()) {
			System.out.println("\nQ9: Approximately, what is the total distance traveled by people within the State of New Jersey in the year 2012, satisfying the Home - Work - Entertainment moving pattern, not necessarily consecutive, lasting at least 4 hours?" 
					+ "\nResult: " + rs.getString(1));
			count++;
		}

		long t2 = System.currentTimeMillis();
		TimeQ time = new TimeQ(t1, t2, "Q9", count);
		return time;
	}

//	--10 On average, how long did it take people to visit Central Park and then Times Square on a clear morning?
	private static TimeQ queryQ10() throws SQLException {
		long t1 = System.currentTimeMillis();

		String sql = "WITH stopCP AS (" + "  select num_trajectory, f.id_user, f.position"
				+ "  from fato f, tb_poi dimPoi" + "  where f.id_poi = dimPoi.id"
				+ "		and dimPoi.name ~* '(Central Park)'" + "  group by num_trajectory, f.id_user, f.position" + " )"
				+ " select SUM(f.duration)/count(f.id_user)"
				+ " from fato f, tb_poi dimPoi, tb_aspect dimAspect, tb_time dimTime, stopCP"
				+ " where f.id_poi = dimPoi.id and f.id_aspect = dimAspect.id and f.id_time = dimTime.id"
				+ "	and dimPoi.name like '%Times Square%'" + "	and dimAspect.value like 'Clear%'"
				+ "	and dimTime.hour >= 5 and dimTime.hour < 12" + "	and f.num_trajectory = stopCP.num_trajectory"
				+ "	and f.id_user = stopCP.id_user" + "	and f.position = stopCP.position+1";
		ResultSet rs = st.executeQuery(sql);

		long count = 0;
		while (rs.next()) {
			System.out.println("\nQ10: On average, how long did it take people to visit Central Park and then Times Square on a clear morning?" 
					+ "\nResult: " + rs.getString(1));
			count++;
		}

		long t2 = System.currentTimeMillis();
		TimeQ time = new TimeQ(t1, t2, "Q10", count);
		return time;
	}

//	-- 11 In 2012, what is the total distance traveled by people who started at the New York Sports Clubs, and some time later used a subway to get to the Mall?
	private static TimeQ queryQ11() throws SQLException {
		long t1 = System.currentTimeMillis();

		String sql = "WITH startNY AS (" + "  select num_trajectory, f.id_user, f.position"
				+ "  from fato f, tb_poi dimPoi" + "  where f.id_poi = dimPoi.id"
				+ "		and dimPoi.name ~* 'New York Sports Clubs'" + "  group by num_trajectory, f.id_user, f.position"
				+ " )," + " transport AS (" + "  select num_trajectory, f.id_user, f.position"
				+ "  from fato f, tb_poi dimPoi" + "  where f.id_poi = dimPoi.id"
				+ "		and dimPoi.category = 'Travel Transport'" + "		and dimPoi.name ~* '(subway)'"
				+ "  group by num_trajectory, f.id_user, f.position" + " )" + " select SUM(f.total_distance) "
				+ " from fato f, tb_poi dimPoi, tb_time dimTime, startNY, transport"
				+ " where f.id_poi = dimPoi.id and f.id_time = dimTime.id" + "	and dimPoi.category = 'mall'"
				+ "	and f.num_trajectory = transport.num_trajectory" + "	and f.id_user =transport.id_user"
				+ "	and f.num_trajectory = startNY.num_trajectory" + "	and f.id_user =startNY.id_user"
				+ "	and f.position > transport.position" + "	and transport.position > startNY.position"
				+ "	and dimTime.year = 2012	";
		ResultSet rs = st.executeQuery(sql);
		long count = 0;
		while (rs.next()) {
			System.out.println("\nQ11: In 2012, what is the total distance traveled by people who started at the New York Sports Clubs, and some time later used a subway to get to the Mall?" 
					+ "\nResult: " + rs.getString(1));
			count++;
		}

		long t2 = System.currentTimeMillis();
		TimeQ time = new TimeQ(t1, t2, "Q11", count);
		return time;
	}

//	--12 What is the average length per month of trajectories that started at some entertainment venue, then spent some time in Central Park, and ended up somewhere in New York that was highly rated and expensive
	private static TimeQ queryQ12() throws SQLException {
		long t1 = System.currentTimeMillis();

		String sql = "WITH stopEnt AS (" + "  select num_trajectory, f.id_user, f.position"
				+ "  from fato f, tb_poi dimPoi" + "  where f.id_poi = dimPoi.id"
				+ "		and dimPoi.category = 'Entertainment'" + "  group by num_trajectory, f.id_user, f.position"
				+ " )," + " stopCP AS (" + "  select num_trajectory, f.id_user, f.position"
				+ "  from fato f, tb_poi dimPoi" + "  where f.id_poi = dimPoi.id"
				+ "		and dimPoi.name ~* 'Central Park'" + "  group by num_trajectory, f.id_user, f.position" + " )"
				+ " select SUM(f.total_duration), dimTime.month, dimTime.year"
				+ " from fato f, tb_poi dimPoi, tb_aspect dimAspect, tb_time dimTime,stopEnt,stopCP"
				+ " where f.id_poi = dimPoi.id and f.id_aspect = dimAspect.id and f.id_time = dimTime.id "
				+ "	and f.num_trajectory = stopEnt.num_trajectory" + "	and f.id_user = stopEnt.id_user"
				+ "	and f.num_trajectory = stopCP.num_trajectory" + "	and f.id_user = stopCP.id_user"
				+ "	and f.position > stopCP.position" + "	and stopCP.position > stopEnt.position"
				+ "	and dimPoi.city = 'New York'"
				+ "	and (substring(dimAspect.value from ',.*,([+-]?([0-9]*[.])?[0-9]+)')::numeric) > 1 "
				+ "	and (substring(dimAspect.value from ',([+-]?([0-9]*[.])?[0-9]+),')::numeric) > 6 "
				+ " group by dimTime.year, dimTime.month ";
		ResultSet rs = st.executeQuery(sql);
		long count = 0;
		System.out.println("\nQ12: What is the average length per month of trajectories that started at some entertainment venue, then spent some time in Central Park, and ended up somewhere in New York that was highly rated and expensive?");
		System.out.println("Results:");
		while (rs.next()) {
			System.out.println(rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3));
			count++;
		}

		long t2 = System.currentTimeMillis();
		TimeQ time = new TimeQ(t1, t2, "Q12", count);
		return time;
	}
}