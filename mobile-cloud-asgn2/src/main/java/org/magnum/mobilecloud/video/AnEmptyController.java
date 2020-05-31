/*
 * 
 * Copyright 2014 Jules White
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package org.magnum.mobilecloud.video;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import org.magnum.mobilecloud.services.VideoService;
import org.magnum.mobilecloud.video.repository.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnEmptyController {
	
	/**
	 * You will need to create one or more Spring controllers to fulfill the
	 * requirements of the assignment. If you use this file, please rename it
	 * to something other than "AnEmptyController"
	 * 
	 * 
		 ________  ________  ________  ________          ___       ___  ___  ________  ___  __       
		|\   ____\|\   __  \|\   __  \|\   ___ \        |\  \     |\  \|\  \|\   ____\|\  \|\  \     
		\ \  \___|\ \  \|\  \ \  \|\  \ \  \_|\ \       \ \  \    \ \  \\\  \ \  \___|\ \  \/  /|_   
		 \ \  \  __\ \  \\\  \ \  \\\  \ \  \ \\ \       \ \  \    \ \  \\\  \ \  \    \ \   ___  \  
		  \ \  \|\  \ \  \\\  \ \  \\\  \ \  \_\\ \       \ \  \____\ \  \\\  \ \  \____\ \  \\ \  \ 
		   \ \_______\ \_______\ \_______\ \_______\       \ \_______\ \_______\ \_______\ \__\\ \__\
		    \|_______|\|_______|\|_______|\|_______|        \|_______|\|_______|\|_______|\|__| \|__|
                                                                                                                                                                                                                                                                        
	 * 
	 */
	
	@Autowired
    private VideoService videoService;

    @RequestMapping(method = RequestMethod.GET, value = "/video")
    public Collection<Video> getVideoList() {
        return videoService.getVideoList();
        //return Collections.emptyList();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/video/{id}")
    public Video getVideoById(@PathVariable("id") long id, HttpServletResponse response) throws IOException {
    	if(videoService.getVideoById(id)==null) {
    		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    		return null;
    	}
    	else {
    		response.setStatus(HttpServletResponse.SC_OK);
    		return videoService.getVideoById(id);
    	}
    }

    @RequestMapping(method = RequestMethod.POST, value = "/video")
    public Video addVideo(@RequestBody Video v) {
        return videoService.addVideo(v);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/video/{id}/like")
    public void likeVideo(@PathVariable("id") long id, Principal p, HttpServletResponse response) throws IOException {
    	Video v = videoService.getVideoById(id);
    	if(v==null)
        	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        else {
        	response.setStatus(HttpServletResponse.SC_OK);
        	videoService.likeVideo(id, p);
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "/video/{id}/unlike")
    public void unlikeVideo(@PathVariable("id") long id, Principal p, HttpServletResponse response) throws IOException {
        
        if(videoService.getVideoById(id)==null)
        	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        else {
        	response.setStatus(HttpServletResponse.SC_OK);
        	videoService.unLikeVideo(id, p);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/video/search/findByName")
    public Collection<Video> findByTitle(@RequestParam("title") String title) {
        return videoService.getVideoByTitle(title);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/video/search/findByDurationLessThan")
    public Collection<Video> findByDurationLessThan(@RequestParam("duration") long duration) {
        return videoService.getVideoByDurationLessThan(duration);
    }
	
	
	@RequestMapping(value="/go",method=RequestMethod.GET)
	public @ResponseBody String goodLuck(){
		return "Good Luck!";
	}
	
	
	
}
