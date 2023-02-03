package com.example.projectwhithapi

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley


class MainActivity : AppCompatActivity() {
    var userList = arrayListOf<User>()
    val apiSample = "https://reqres.in/api/users"
    var recyclerView: RecyclerView? = null

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        val reqQueue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(Request.Method.GET, apiSample, null,{ res->
            Log.d("Volley Sample",res.getString("page"))

            val jsonArray = res.getJSONArray("data")
            for(i in 0 until jsonArray.length()){
                val jsonObj = jsonArray.getJSONObject(i)
                //Log.d("Volley Sample", jsonObj.toString())

                val user = User(
                    jsonObj.getInt("id"),
                    jsonObj.getString("email"),
                    jsonObj.getString("first_name"),
                    jsonObj.getString("last_name"),
                    jsonObj.getString("avatar")
                )
                    userList.add(user)
                    //Log.d("Volley Sample", userList.toString())
            }
            //in two vertical lines
            //recyclerView?.layoutManager = GridLayoutManager(this,2)
            recyclerView?.layoutManager = LinearLayoutManager(this)
            recyclerView?.adapter = UserAdapter(userList)


        },{err ->
            Log.d("Volley Sample Fail",err.message.toString())
        })
            /*StringRequest(Request.Method.GET,apiSample,{result->
            Log.d("Volley Example",result.toString())
        },{err ->

            Log.d("Volley Example", err.message.toString())
        })*/

        reqQueue.add(request)

    }
}