# Pagy Lost & Found

This is a demo application for Spring Boot to track lost items. It enabled me to evaluate few cloud services. The idea arose from a discussion in a
[Facebook group](https://www.facebook.com/groups/235855133202437/permalink/1344056399048966/?comment_id=1344098629044743&comment_tracking=%7B%22tn%22%3A%22R7%22%7D)
(see below: [Pagy](#pagy)). Respective person suggesting the idea may add her name here by making a pull request if she so chooses.

## Live demo

You can try out a live demo for which availability or currentness is not quaranteed. See: <https://pagylostandfound.kari.iki.fi/>

## Spring Boot

Spring has made it easier to approach the framework by narrowing the learning gap in starting to use the versatile giant. So they presented [Spring Boot](https://projects.spring.io/spring-boot/).

I have glorified Spring a lot on my other projects. I'm sure you already know what I'm talking about.

## Evaluation of cloud services

Google has built a comprehensive set of services in the cloud (among others). Cloud services enable us to get out of the chains of physical server hardware and management of virtual servers. This makes it possible to write portable source code so that you don't have to make a scrappy circus out of every migration between servers, platforms or locations.

### Google Cloud Storage
Google provides a blob storage that they call buckets. You can store your blobs in a bucket no matter how small or big they are. A fantastic feature of the storage is that you can distribute the files on the Google's massive and global infrastructure. You have few choices on the availability of your data.

This gives us few benefits. We don't need to store persistent data on the server itself, which makes it possible to consider the server ephemeral. You can deploy a virtual server on the cloud or even fire a lightweight container to serve your application's source code.

As well as the server is ephemeral, the service is portable. While planning on migrating services to other platform you are happy to know it will be swift as long as you know connecting to the data is effortless.

While your blobs are in the cloud, you are quite safe against service disruptions and the risk of loosing your data for hardware malfunction or else. Yet this doesn't mean that you are free from designing a good backup plan. The strong availability of data doesn't protect you against errors in your own source code and probable weaknesses in the framework or middleware you are using.

### Google Cloud Sql
Google has constructed a quite inexpensive database service in the cloud. You may fire up a Mysql database service in minutes with a click of few buttons. No need for complex configuration or deciding between database engines.

Again your data is separated from the server or computing platform.

Google Cloud Sql apparently makes it also easy to handle backups even in any point of time. Above that also importing and exporting databases have been made easy. Not that I have yet had an opportunity to test that. Let us see whether exportable data finds the demo service.

### Google Maps API
Google has offered it's well known maps for developers to add additional features and for use in their services. I have fiddled with Google Maps before, but a lot has changed since. Seems that basic functionality is the same as few years ago, but a lot of improvements and features have been added.

Now in this project we have only utilised the Javascript API. It would be interesting to do calculations and map analytincs on the backend and present improved information for the users.

## Hindrances found on the way

### Facebook inoperability with googleapis.com/download

Facebook considers URL googleapis.com/download harmful and blocks it. 

> The content you're trying to share includes a link that's been blocked for being spammy or unsafe

The error message refers to an unsecure http url for googleapis.com although all the traffic on the site itself is redirected to secured https url and the links to outside resources are secured as well.

My deduction of the issue is that people do share harmful content on Google's services. The few breaking the rules again spoil the fun also for all the rest. At the moment of writing I have reported the site as safe and concluded a profile on WOT.

If the matter can't be fixed by regular reporting processes I guess there isn't a resolution other than relaying the data from cloud services via the server itself. We still have the benefit of considering the server ephemeral and storing persistent data elsewhere.

### Content Security Policy

This is not a new thing. If you want to look good on security scanners, you need to pay attention to the configuration of your http service. It is a good practise to allow XSS to only those sites that you actually work with and trust on. This doesn't really play well along with cloud services.

Cloud services don't publish service endpoint addresses very openly. You kind of learn them on your journey through the development process. More cloud services you utilise, longer becomes the list of cloud and API endpoints you need to manage on your server to make it secure.

And of course, services evolve. The list you managed to gather together today might easily go out of date tomorrow.

### Mysql SSL

It's only safe to use cloud sql service if the traffic in between the server and th database service is encrypted. However, the encryption is quite difficult to configure as the keys and certificates need to be imported into a Java keystore.

Once you guide your Java Runtime Engine to use the truststore holding the certificate for the database server, the JRE doesn't trust any other service if it is not presented in the truststore. On resolution is to add the database service certificate to your cacerts.jks. I'm not so keen on this, but have heard that there might be a better solution in just placing trusted certificates in a directory and hashing them somwhow. Need to research this more. I'm quite sure internet has plenty of information on this.


## Pagy

[Pähkinärinne](https://en.wikipedia.org/wiki/Pähkinärinne) aka Pägy is a vibrant and beautiful suburb of the city of Vantaa located just in the crossing of Helsinki, Espoo and Vantaa city limits. The name of the suburb probably refers to hazel trees that have crown in the area for who knows how long. Disticntional attribute of the suburb are brick and concrete colored apartment buildings.

Most of Pägy was built in the seventies, but there is quite a lot of new vivacious movement going on in the area in the form of construction and improvement. Capital area is crowing fast and the need for smaller apartments is imminent. Thus the construction and mapping of new areas to build on is an ongoing movement.

## Roadmap and improvements

This was a project for literally rainy days. Now the summer has reached us again so don't hold your breath in waiting for FREs.

* Show item type in the list on the front page
* Show item date in the list on the front page
* Remove hardcoded elements from the templates and source code
* Make final fields final
* Write unit tests
* Implement test automation and CI
* Implement deployment automation
* Periodical search and delete -process to remove orphaned picture files on the bucket
* Unvalidation of old item listings (let me be forgotten)
* Privacy Policy
* Different types of item lists on front page
   * Items reported by me
* Different kind of maps
   * Cluster map
   * Heat map
* Coupling of items
   * Locate lost items on the map to found items nearby (could we utilise Google Places API to analyse the proximity of items?)
   * Allow to tag possible lost&found pairings
* Tagging feature for the items (bicycles, keys, etc in their respective groups)
* Feature to add key/value pairs of details on items
   * E.g. make of a found item
   * Additional characteristics of an item
* Convert these FREs to issues on the project repository
* Convert this prosaic story to a wiki page and write more compact readme

## Other ideas for services

* How about a mapping service, where people could mark their favorite places on a map and add some social media features and analytics on above the listings?
