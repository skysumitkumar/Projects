package com.sumit.JobApp.Controller;


import com.sumit.JobApp.service.JobService;
import com.sumit.JobApp.model.JobPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//when we are using controller i bydefault think you are returning a view name
@RestController
//to give the data to the react because react host on 3000
@CrossOrigin(origins="http://localhost:3000")
public class JobRestController {

    @Autowired
    private JobService service;

    //here we say this method only return json data
    @GetMapping(path="jobPosts",produces={"application/json"})
    //to returning the data on the web we can use @ResponseBody to see it go to http://localhost:8080/jobPosts
    @ResponseBody
    public List<JobPost> getAllJobs()
    {
        return service.getAllJobs();
    }

    @GetMapping("jobPost/{postId}")
    //here we get the data from the url and give the data of the specific id for this we use @PathVariable
    public JobPost getJob(@PathVariable("postId")int postId)
    {
        return service.getJob(postId);
    }

    @GetMapping("jobPosts/keyword/{keyword}")
    public List<JobPost>searchByKeyword(@PathVariable("keyword") String keyword)
    {
        return service.search(keyword);
    }

    //here we said this method only consume xml data
    @PostMapping(path = "jobPost",consumes={"aopplication/xml"})
    //to send json data to the server we use @RequestBody to store json data to JobPost
    public JobPost addjob(@RequestBody JobPost jobPost)
    {
        service.addJob(jobPost);
        return service.getJob(jobPost.getPostId());
    }

    //put mapping for the change data to the server
    @PutMapping("jobPost")
    public JobPost updateJob(@RequestBody JobPost jobPost)
    {
        service.updateJob(jobPost);
        return service.getJob(jobPost.getPostId());
    }

    //to delete the data from the server with the help of postId we use @DeleteMapping
    @DeleteMapping("jobPost/{postId}")//here postId is taken from url
    public String deleteJob(@PathVariable int postId)
    {
        service.deleteJob(postId);
        return "Deleted Job Id: " + postId;
    }

    @GetMapping("load")
    public String loadData()
    {
        service.load();
        return "success";
    }
}
