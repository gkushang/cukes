var project.one = "oraTest";
var project.two = "campaignManagerTest";
var workspace =  "/Users/kugajjar/Documents/workspace/";"

var projects = [
                    	{
                    		"_class" : "com.cukesrepo.domain.Project",
                    		"name" : project.one,
                    		"repositorypath" : workspace+project.one
                    	},
                    	{
                    		"_class" : "com.cukesrepo.domain.Project",
                    		"name" : project.two,
                    		"repositorypath" : workspace+project.two
                    	}
                    ]

db.project.remove({"name" : project.one});
db.project.remove({"name" : project.two});
db.project.insertAll(projects);
