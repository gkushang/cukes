var projectone = "oraTest";
var projecttwo = "campaignManagerTest";
var workspace =  "/Users/kugajjar/Documents/workspace/";

var projects = [   	{
                    		"_class" : "com.cukesrepo.domain.Project",
                    		"name" : projectone,
                    		"repositorypath" : workspace+projectone
                    	},
                    	{
                    		"_class" : "com.cukesrepo.domain.Project",
                    		"name" : projecttwo,
                    		"repositorypath" : workspace+projecttwo
                    	}
                ]

db.project.remove({"name" : projectone});
db.project.remove({"name" : projecttwo});
db.project.insert(projects);
