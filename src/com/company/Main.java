package com.company;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    static HashMap<String,User> users = new HashMap<>();
    static ArrayList<User> pastUsers = new ArrayList<>();

    public static void main(String[] args) {
        Spark.get(
                "/",
                (request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("userName");
                    User user = users.get(name);


                    HashMap m = new HashMap();
                    if (user != null) {
                        m.put("name",user.name);

                    }
                    m.put("pastUsers",pastUsers);
                    return new ModelAndView(m,"home.html");
                },
                new MustacheTemplateEngine()
        );
        Spark.get(
                "/login",
                (request, response) -> {
                    return new ModelAndView(null, "login.html");
                },
                new MustacheTemplateEngine()
        );
        Spark.post(
                "/login",
                (request, response) -> {
                    String name = request.queryParams("userName");
                    User user = users.get(name);
                    if (user == null){
                        user = new User(name);
                        users.put(name,user);
                    }

                    Session session = request.session();
                    session.attribute("userName", name);

                    pastUsers.add(user);
                    users.put(name,user);
                    response.redirect("/");
                    return null;
                }
        );
        Spark.post(
                "/logout",
                (request, response) -> {
                    Session session = request.session();
                    session.invalidate();

                    response.redirect("/");
                    return null;
                }
        );

    }
}
