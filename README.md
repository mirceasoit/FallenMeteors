# FallenMeteors
Simple Android app that will display a list of fallen meteors on Earth. 
- the meteors list is sorted by size and filtered from year 1900;
- when a meteor is selected a map is presented with a pin that indicates the location of the fallen meteor;
- the data is fetched on app launch and the user have the possibility to refresh the data while using the app;

The data points are available via the NASA API: 
● view: https://data.nasa.gov/view/ak9y-cwf9  
● documentation: https://dev.socrata.com/foundry/data.nasa.gov/y77d-th95  
● json: https://data.nasa.gov/resource/y77d-th95.json  
● administration: https://data.nasa.gov/login   

The number of requests is limited unless you sign up for X-App-Token (which you can do for free).

You will need also a Google Maps Api key that needs to be placed in local.properties:
MAPS_API_KEY=your_api_key

Arhitecture used: MVVM

There are three implementation variants:

● Retrofit with MoshiConverterFactory (using Call<T> interface)  -  Repository (using Callback<T> and a custom DataResponseCallback inteface) - ViewModel (using LiveData and Sealed Classes)
  
● Retrofit with MoshiConverterFactory and RxJava2CallAdapterFactory (using Observable<T>) - Repository (using Observable and and a custom DataResponseCallback inteface) - ViewModel (using LiveData and Sealed Classes)
  
●  Retrofit with MoshiConverterFactory (using suspend functions) - Repository (using suspend functions) - ViewModel (using LiveData and Sealed Classes)  


Tools used:
    //LiveData
    implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.2.0'
    //ViewModel
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0'
    //Lifecycle extensions
    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
    //Activity library
    implementation "androidx.activity:activity-ktx:1.1.0"
    //Retrofit
    implementation "com.squareup.retrofit2:retrofit:2.9.0"
    //Moshi
    implementation "com.squareup.retrofit2:converter-moshi:2.5.0"
    implementation 'com.squareup.retrofit2:converter-scalars:2.9.0'
    //RxJava
    implementation 'io.reactivex.rxjava2:rxjava:2.2.10'
    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
    implementation 'io.reactivex.rxjava2:rxandroid:2.0.2'
    //Maps
    implementation 'com.google.android.gms:play-services-maps:17.0.0'
    //OkHttp3 Logging interceptor
    implementation "com.squareup.okhttp3:logging-interceptor:4.8.1"
