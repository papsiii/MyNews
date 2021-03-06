package com.mynews.flooo.mynews.Controllers.ApiRest;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


// This is a class who creating the request for the Api.

public class BuildRequest
{

    private SharedPreferences sharedPreferences;
    private static HashMap<String, Boolean> checkBoxStates = new HashMap<String, Boolean>();
    private static String queryTerm;



    // Building the query terms

    public String queryTermBuild(String input)
    {
        if(input.equals("")){return null;}

        StringBuilder queryTermString =new StringBuilder();

        for (String value: input.split(" "))
        {
            queryTermString.append(value);
            queryTermString.append("+");

        }

        return queryTermString.toString();
    }


    // We used a hashmap to get the section who are enabled to the research
    // The sections are loading with the SharedPreferences.

    public String buildSectionsStringForNotification(Context c)
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);

        checkBoxStates.clear();
        checkBoxStates.put("Arts", sharedPreferences.getBoolean("Arts",true));
        checkBoxStates.put("Sports", sharedPreferences.getBoolean("Sports",true));
        checkBoxStates.put("Politics", sharedPreferences.getBoolean("Politics",true));
        checkBoxStates.put("Entrepreneurs", sharedPreferences.getBoolean("Entrepreneurs",true));
        checkBoxStates.put("Business", sharedPreferences.getBoolean("Business",true));
        checkBoxStates.put("Travel", sharedPreferences.getBoolean("Travel",true));

        return buildNewsDeskQuery();

    }

    public String buildSectionsStringForSearch(ArrayList<String> list)
    {
        checkBoxStates.clear();

        for(String key:list)
        {
            checkBoxStates.put(key,true);
        }

        return buildNewsDeskQuery();

    }

    public String getQueryTerm()
    {
        return queryTermBuild(sharedPreferences.getString("QueryTerm",""));
    }

    private String buildNewsDeskQuery()
    {
        StringBuilder sectionsString = new StringBuilder();
        sectionsString.append("news_desk:(");

        for(Map.Entry<String, Boolean> entry : checkBoxStates.entrySet())
        {

            if(entry.getValue())
            {
                sectionsString.append("\"");
                sectionsString.append(entry.getKey());
                sectionsString.append("\" ");

            }

        }

        sectionsString.append(")");
        String sections= sectionsString.toString();
        System.out.println(sections);
        return sections;
    }
}
