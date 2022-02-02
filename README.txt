Software 2 Database Management GUI
author: Jeremy Hennessy
contact: jhenn44@wgu.edu
student application version: 1.0
date: 6/8/2021
IDE: IntelliJ 2020.3.2
JDK 11.0.10
JavaFX-sdk-11.0.2
MySQL-connector-java-8.0.22


== Purpose ==

The purpose of this program is have an interactive Graphical UI that allows an authorized user to view, and modify
a database corresponding to customer appointments.  The interface is meant to be capable of use in various time zones
and should have login information capable to be displayed in French if in a French TimeZone

== Description of Use ==

Once the program is launched the user will be required to enter a user name and password that corresponds to a user in
the database in order to continue.  All login attempts will be saved to a separate file called login_activiy.txt.  Once
logged in the user can view, add, and modify all customers and appointments in the database.  Appointments will only
be able to be created for times corresponding between 8AM and 10PM EST.  Schedules for each contact and customer in
the database can also be viewed.  A report for the types and appointments per month can also be generated in the main
interface using combo box selections.  To modify or delete an appointment or customer, a customer or appointment must
be selected from it's corresponding table.  No customers with upcoming appointments can be deleted.

== Extra Report ==
The extra report generated is a schedule for each customer in the database