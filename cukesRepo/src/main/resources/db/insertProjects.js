var project.one = "oraTest";
var project.two = "campaignManagerTest";

var projects = [
                    	{
                    		"_class" : "com.cukesrepo.domain.Project",
                    		"name" : project.one,
                    		"repositorypath" : "/Users/kugajjar/Documents/workspace/oraTest"
                    	},
                    	{
                    		"_class" : "com.cukesrepo.domain.Project",
                    		"name" : project.two,
                    		"repositorypath" : "/Users/kugajjar/Documents/workspace/campaignManagerTest"
                    	}
                    ]

db.project.remove({"name" : project.one});
db.project.remove({"name" : project.two});
db.project.insert(projects);
