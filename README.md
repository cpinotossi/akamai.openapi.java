# Akamai API Demo
## What will we show
1. Easy use of the API to upload content to the OTA service.
2. Easy way to use the OTA reporting.

## Requirements
### Installed software on your computer
- Java 8
- cURL
- MP3 Player (MacOs comes with afplay on command shell)
- Browser
- 
### Get the java opencli client.
Download the jar file from here:
https://drive.google.com/file/d/1mXVVe3T48r3G82gdMvoYQoOEIpO_mneq/view?usp=sharing

### Get the Open API client credentials
Download the .edgerc file from here:
https://drive.google.com/file/d/1Vp7oWDS1PmurDqDn_Nrgc5NDZdUSYwnm/view?usp=sharing

### Get the sample file
The sample file will be used during the whole demo.
https://drive.google.com/file/d/1asObCLw4_1z3Hsapx8dXaTMRGKM6NEYl/view?usp=sharing

### Call the java opencli client the first time
After downloading the opencli.jar open a shell and go to the directory under which you downloaded the opencli.jar and execute the following command:
```
java -jar opencli.jar
try '--help' or '-h' for more information
```
Call the help function:
~~~~
java -jar opencli.jar -h                                                                              ⏎
Expected a value after parameter -h
Usage: <main class> [options] [command] [command options]
  Options:
    --edgerc
      Full path to the credentials file. Default: <user.home>/.edgerc
    --help

  * --hostname, -h
      Hostname (Property) referenced inside an akamai configuration.
    --nssection, -n
      Netstorage Upload Client Name inside the edgerc. Default:
      api-client-netstorage
    --psection, -p
      Purge API Client Name inside the edgerc. Default: api-client-purge
    --section
      API Client Name inside the edgerc. Default: api-client
  Commands:
  ...
~~~~
You should see more output but for now we just need to focus on making sure to provide the following two parameters:
1. --edgerc Full path to the .edgerc file which you did download.
2. --hostname, in our case it will be always "ota.edgegate.de"

All your request should look as follow in case the .edgerc file is stored on your home directory under a Mac:
~~~~
java -jar opencli.jar -h ota.edgegate.de --edgerc /Users/cpinotos/.edgerc <command> <parameters>
~~~~
# Demo step by step
## Upload file to netstorage
The structure of the upload path which will be generated on Netstorage needs to follow certain rules so OTA features can be applied.
The upload path needs to be structured as follow:
1. It needs to be followed by the folder "/updates". Only under this folder we will apply OTA features from the corresponding Akamai configuration.
2. The last path component of the url (folder) needs to represent a name for a group of clients, something like a model Name or something.
3. The filename (w/o the extension) needs to represent the name of a certain update/release.


Example:
~~~~
/updates/cpinotos/model_1/mozart_20180302.mp3
~~~~

This Upload URL does represent:

1. "/uploads" the required OTA folder for the used Akamai configuration.
2. "model_1", a name of a group of clients. In our case all clients are of model type "model_1".
3. "mozart_20180302" does represent the name of an update (campaign) which needs to be provided to all clients which are of type "model_1".

NOTE: you are free to add us much folders between 2) and 3) as you like.
That´s what we did here by adding "/cpinotos" to ensure not to run into conflicts with other users.

### API call to upload to NetStorage
```
java -jar opencli.jar -h ota.edgegate.de --edgerc /Users/cpinotos/.edgerc upload -i mozart40.mp3 -o /updates/cpinotos/model_1/mozart_20180302.mp3
Local File (in):mozart40.mp3
Netstorage Location (out):/599907/updates/cpinotos/model_1/mozart_20180302.mp3
done
```
IMPORTANT

## Verify if file has been uploaded to netstorage
```
java -jar opencli.jar -h ota.edgegate.de --edgerc /Users/cpinotos/.edgerc dir -o /updates/cpinotos/model_1
{
  "file": [
    {
      "name": "mozart_20180302.mp3",
      "type": "file",
      "mtime": "1519661946",
      "size": "8614138",
      "md5": "f916b03cb89b80fc3417a08f643cf563"
    }
  ],
  "directory": "/599907/updates/cpinotos/model_1"
}
```
Like you can see the file "mozart_20180302.mp3 has been uploaded to the Netstorage under the CPCode Folder "599907".
This CPCode Folder is defined inside your .edgerc as part of your API Section (ns_cpcode):

```
[api-client]
client_portal_name = otaadmin1
client_portal_account = Ion_Standard_Beta_Jam_3
client_secret = TvKGAmRc+8ZdbR6d56xdvfKyY0ZWj2njAI8ccPoBtC8=
host = akab-fm3x7q5gk3zmv7ry-mtidyfbxiisjytzn.luna.akamaiapis.net
access_token = akab-qt5wdeg7rpivsw3o-ga6au45akxer6yux
client_token = akab-h4fqhcewzdetbhpz-kfineolyschudoc3
ns_client = otatest1
ns_key = 8t4U9GBPOBm4cvU3EsGw8u3b2BCNr371TAyqqj2c32DsZ3m5Ep
ns_host = ionstandard3-nsu.akamaihd.net
ns_cpcode = 599907
```

The Netsorage does create an MD5 has which can be used to validate if the content is correct.

On Mac you can compare both on the terminal as follow:

```
md5 mozart40.mp3
MD5 (mozart40.mp3) = f916b03cb89b80fc3417a08f643cf563
```

## Download via cURL
```
curl -v -k -o mozart_20180302.mp3  "https://ota.edgegate.de/updates/cpinotos/model_1/mozart_20180302.mp3"

>
< HTTP/1.1 403 Forbidden
< Server: AkamaiGHost
< Mime-Version: 1.0
< Content-Type: text/html
< Content-Length: 333
< X-Reference-Error: 18.c6f21502.1519994463.134904ed
< Expires: Fri, 02 Mar 2018 12:41:03 GMT
< Date: Fri, 02 Mar 2018 12:41:03 GMT
```
### Verify why the request did fail
We received an 403. In additon we received the X-Reference-Error code inside the HTTP Response Header (18.2d373217.1519662458.bf5d8e).

IMPORTANT: The following call can take a while (~5 min).

```
java -jar opencli.jar -h ota.edgegate.de --edgerc /Users/cpinotos/.edgerc translate_error -e 18.c6f21502.1519994463.134904ed
{
  "translatedError": {
    "url": "http\u0026#x3a;\u0026#x2f;\u0026#x2f;ota.edgegate.de\u0026#x2f;updates\u0026#x2f;cpinotos\u0026#x2f;model_1\u0026#x2f;mozart_20180302.mp3",
    "httpResponseCode": 403,
    "timestamp": "Fri, Mar 2, 2018 12:41 GMT",
    "epochTime": 1519994460,
    "clientIp": "^ (Client IP - NA)",
    "connectingIp": "2001:4878:8204:310:2d3a:534:b3cd:7d3d (MUNICH,BY,DE)",
    "serverIp": "2.21.242.198 (FRANKFURT,HE,DE)",
    "originHostname": "NA",
    "originIp": "NA",
    "userAgent": "curl\u0026#x2f;7.54.0",
    "requestMethod": "GET",
    "reasonForFailure": "ERR_ACCESS_DENIED",
    "wafDetails": "-",
    "logs": [
      {
        "description": "Edge Server: 2.21.242.198 response to 2001",
        "fields": {
          "Ghost IP": "2.21.242.198",
          "Client Request": "r",
          "timestamp": "1519994463.852",
          "object size": "333",
          "content bytes served": "333",
          "total estimated bytes served": "4388",
          "client IP": "2001",
          "HTTP method": "GET",
          "ARL": "\u0026#x2f;\u0026#x5e;\u0026#x2f;150\u0026#x2f;599907\u0026#x2f;\u0026#x5e;\u0026#x2f;ionstandard3.download.akamai.com\u0026#x2f;updates\u0026#x2f;cpinotos\u0026#x2f;model_1\u0026#x2f;mozart_20180302.mp3",
          "HTTP status code": "403",
          "error": "ERR_ACCESS_DENIED",
          "content-type": "text\u0026#x2f;html",
          "host header": "ota.edgegate.de",
          "cookie": "-",
          "referrer": "-",
          "user-agent": "curl\u0026#x2f;7.54.0",
          "IMS": "-",
          "SSL": "C",
          "persistent request number": "1",
          "Client request header size": "260",
          "Accept-Language": "\u0026#x5e;",
          "SSL overhead bytes": "2691",
          "Serial number and map": "150.\u0026#x5e;",
          "Request byte-range": "-",
          "Uncompressed length": "-",
          "Other-Error-Indication": "\u0026#x5e;",
          "dca-data": "-",
          "X-Forwarded-For": "-",
          "X-Akamai-Edge-Log": "-",
          "object-max-age_s": "600",
          "custom-field": "Geo",
          "object-status-2": "or",
          "ssl-byte": "\u0026#x5e;",
          "c-http-overhead": "\u0026#x5e;",
          "Client-rate-limiting": "\u0026#x5e;",
          "Client-request-body-size": "-",
          "flv seek processing info": "\u0026#x5e;",
          "True client ip": "\u0026#x5e;",
          "Web Application Firewall Information": "-",
          "Edge Tokenization Information": "-",
          "Origin File Size": "\u0026#x5e;",
          "HTTP Streaming info": "-",
          "Reason for not caching (priv/released)": "\u0026#x5e;",
          "Rate Accounting info": "\u0026#x5e;"
        }
      }
    ]
  }
}
```
Look for the Error Description:
```
"error": "ERR_ACCESS_DENIED\u0026#x7c;ptk_edgeauth-missing-token".
The term "edgeauth-missing-token" does indicate that we need to provide an valid EdgeAuth Token.
```

In addition you can highlight things like:
* "clientIp": "^ (Client IP - NA)",
* "connectingIp": "2001:4878:8204:310:2d3a:534:b3cd:7d3d (MUNICH,BY,DE)",
* "serverIp": "2.21.242.198 (FRANKFURT,HE,DE)",

This parameter tell more about where the issue did take place and could become handy if further investigation are needed.

## Generate EdgeAuth Token
IMPORTANT: The following call can take a while (~2 min).
```
java -jar opencli.jar -h ota.edgegate.de --edgerc /Users/cpinotos/.edgerc edgeauth -i /updates/cpinotos/model_1/mozart_20180302.mp3
Start edgeurl:
edgeURL:step1/7: found Property Configuration for Hostname ota.edgegate.de
edgeURL:step2/7: found Property Configuration ota.edgegate.de  version 46
edgeURL:step3/7: downloaded Property Configuration ota.edgegate.de version 46
edgeURL:step4/7: extract edgeauth secret key from Property Configuration ota.edgegate.de version 46
edgeURL:step5/7: extract edgeauth query name from Property Configuration ota.edgegate.de version 46
edgeURL:step6/7: generate edgeauth token with startime: 1519994592
edgeURL:step7/7: EdgeURL:
token=st=1519994592~exp=1519998192~hmac=bd8400653c4b25a53c6bf1cd14d6c714064be0821cd971727b6392fadcf384a3
```

## Download via cURL with Edgeauth Token
```
curl -v -k -o mozart_20180302.mp3 "https://ota.edgegate.de/updates/cpinotos/model_1/mozart_20180302.mp3?token=st=1519994592~exp=1519998192~hmac=bd8400653c4b25a53c6bf1cd14d6c714064be0821cd971727b6392fadcf384a3"
>
< HTTP/1.1 200 OK
< Server: Apache
< ETag: "f916b03cb89b80fc3417a08f643cf563:1519994092"
< Content-MD5: +RawPLibgPw0F6CPZDz1Yw==
< Last-Modified: Fri, 02 Mar 2018 12:34:50 GMT
< Accept-Ranges: bytes
< Content-Length: 8614138
< Content-Type: audio/mpeg
< Date: Fri, 02 Mar 2018 12:44:24 GMT
```

## Play Mp3 File
On a Mac you will be able to play the file from your terminal via the afplay command:
```
afplay mozart_20180302.mp3
```
## Send valid OTA Reporting Request
So far none of the request we did will show up in our OTA aggregated reports. That´s because the used OTA setup used here does expect the http request header "user-agent" with the value "device".

NOTE: That has been done to avoid to track all kind of incoming request. The user-agent does act as a filter for us. 

OTA reporting request in case of ota.edgegate.de needs to include:
- query parameter uid (unique id of a certain device)
- HTTP Header "user-agent: device"
- URL Path needs to be structured as mentioned under section "Upload file to netstorage".

Example cURL request will look as follow:
```
curl -v -k -A"device" -o mozart_20180302.mp3 "https://ota.edgegate.de/updates/cpinotos/model_1/mozart_20180226.mp3?uid=1&token=st=st=1519662859~exp=1519666459~hmac=0999eb705973fa13e57e2a489dd96fe7b707a47391a3e5339d869570e09ef016"
```

## Simulate small traffic load with curl
Simulate 10 request with curl on the command shell (Mac):
```
for i in {1..10}; do eval "curl -o /dev/null -v -k -A'device' 'https://ota.edgegate.de/updates/cpinotos/model_1/mozart_20180302.mp3?uid=$i&token=st=1519994592~exp=1519998192~hmac=bd8400653c4b25a53c6bf1cd14d6c714064be0821cd971727b6392fadcf384a3' 2>&1|grep '^<.*HTTP/1.1'"; done
< HTTP/1.1 200 OK
< HTTP/1.1 200 OK
< HTTP/1.1 200 OK
< HTTP/1.1 200 OK
< HTTP/1.1 200 OK
< HTTP/1.1 200 OK
< HTTP/1.1 200 OK
< HTTP/1.1 200 OK
< HTTP/1.1 200 OK
< HTTP/1.1 200 OK
```

### Request OTA Report
request a simple list of all OTA request of the last 24 hours for CPCode 599907:
```
 java -jar opencli.jar -h ota.edgegate.de --edgerc /Users/cpinotos/.edgerc ota -c 599907 -f list
UID	Download-Time	URL
2	Fri Mar 02 13:46:03 CET 2018	http(s)://ota.edgegate.de/updates/cpinotos/model_1/mozart_20180302.mp3
1	Fri Mar 02 13:46:03 CET 2018	http(s)://ota.edgegate.de/updates/cpinotos/model_1/mozart_20180302.mp3
3	Fri Mar 02 13:46:03 CET 2018	http(s)://ota.edgegate.de/updates/cpinotos/model_1/mozart_20180302.mp3
5	Fri Mar 02 13:46:04 CET 2018	http(s)://ota.edgegate.de/updates/cpinotos/model_1/mozart_20180302.mp3
4	Fri Mar 02 13:46:04 CET 2018	http(s)://ota.edgegate.de/updates/cpinotos/model_1/mozart_20180302.mp3
7	Fri Mar 02 13:46:05 CET 2018	http(s)://ota.edgegate.de/updates/cpinotos/model_1/mozart_20180302.mp3
8	Fri Mar 02 13:46:05 CET 2018	http(s)://ota.edgegate.de/updates/cpinotos/model_1/mozart_20180302.mp3
6	Fri Mar 02 13:46:05 CET 2018	http(s)://ota.edgegate.de/updates/cpinotos/model_1/mozart_20180302.mp3
9	Fri Mar 02 13:46:05 CET 2018	http(s)://ota.edgegate.de/updates/cpinotos/model_1/mozart_20180302.mp3
10	Fri Mar 02 13:46:05 CET 2018	http(s)://ota.edgegate.de/updates/cpinotos/model_1/mozart_20180302.mp3
```
NOTE: we used the flag "-f list" to force a list view. Without this flag we would get the raw JSON response.



## Simulate large traffic with Cloud Test
TBD

## Google Sheet Demo
TBD


# Run your Demo inside Docker
TODO: need to provide an Docker Image
