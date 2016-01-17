## GoogleSpreadSheetReader

### Instruction:

1. Register [here](https://console.developers.google.com)
2. Create new project
3. Go to ‘API Manager’ -> Credentials
4. Create API key 
5. Create OAuth 2.0 client IDs 
6. Create service account key 
 * service account -> new service account
 * save service account ID *****@learned-house-119223.iam.gserviceaccount.com it will be needed in the code .properties file
 * key type -> p12 and download .p12 file it will be needed as resource

There’s a **user.properties** file in the project where you need to paste the following information:

 1) **spreadsheetUrl**=https://spreadsheets.google.com/feeds/spreadsheets/1vXsuIuq6Chzxg2C4KzH9c5y5f_tHgKjvD_2zYMpww1c
~ When you copy from google drive you get such link - https://docs.google.com/spreadsheets/d/1vXsuIuq6Chzxg2C4KzH9c5y5f_tHgKjvD_2zYMpww1c/edit#gid=0
but you have to change to the following one - https://spreadsheets.google.com/feeds/spreadsheets/1vXsuIuq6Chzxg2C4KzH9c5y5f_tHgKjvD_2zYMpww1c 

 2) **serviceAccountId**=*****@learned-house-119223.iam.gserviceaccount.com
~ This is service account when you generated your .p12 key

 3) **p12FilePath**=/Users/***/key.p12
~ Path to your .p12 file

######useful links with help and information in case of issues that could be faced during this process:*
http://stackoverflow.com/questions/30483601/create-spreadsheet-using-google-spreadsheet-api-in-google-drive-in-java
http://stackoverflow.com/questions/31503398/google-spreadsheet-api-com-google-gdata-util-parseexception-invalid-root-elem

######versions:

<table style="width:100%">
  <tr>
    <td>Version</td>
    <td>Comment</td> 
  </tr>
  <tr>
    <td>1</td>
    <td>Added SpreadSheetReader to read column values from google doc file. Added libs. </td> 
  </tr>
</table>

