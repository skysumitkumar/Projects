package com.sumit.JobApp.service;

import com.sumit.JobApp.model.JobPost;
import com.sumit.JobApp.repository.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class JobService {

    @Autowired
    private JobRepo repo;
    public void addJob(JobPost jobPost)
    {
        //repo.addJob(jobPost);
        repo.save(jobPost);
    }
    public List<JobPost> getAllJobs()
    {
        //return repo.getAllJobs();
        return repo.findAll();
    }

    public JobPost getJob(int postId) {
        //return repo.getJob(i);
        return repo.findById(postId).orElse(new JobPost());
    }

    public void updateJob(JobPost jobPost) {
        //repo.updateJob(jobPost);
        repo.save(jobPost);
    }

    public void deleteJob(int postId) {

        //repo.deleteJob(postId);
        repo.deleteById(postId);
    }

    public void load() {
        //when this is loaded this data is automatically save in the data base
        List<JobPost>jobs=new ArrayList<>(List.of(new JobPost(1,"Java Developer","this is java profile",2,List.of("java"))));
        repo.saveAll(jobs);
    }

    public List<JobPost> search(String keyword) {
        return repo.findByPostProfileContainingOrPostDescContaining(keyword,keyword);
    }
}
