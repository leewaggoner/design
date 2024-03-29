# design
App to show where signs are placed so they can be tracked and removed later.

I am calling MVP done pending testing.

## MVP

### Login
* Login and authorization via Google

### Campaigns Screen
* User can create a campaign for putting up signs
* User can add a note describing the campaign
* User can remove a campaign and all related sign data
* User can choose a campaign in which to track signage

### Map Screen
* User can place a sign marker on the map at the current location and add a note for the sign
* User can remove a sign marker from the map

### Signs Screen
* User can view all their signs as a list.
* User can delete signs from the list


## Version 2
### Login
* Add Facebook authentication
* Add username/password authentication

### Team Screen
* Visible only to admins
* Only admins can add and remove team members from a campaign
* Invitations to a campaign can be sent out to team members, who can then choose to join that campaign

### Campaign Screen
* Only admins can create and remove campaigns
* Admin can view all of the campaigns they have created
* Others can view only the campaigns they are a part of

### Map Screen
* Campaign team members can view the sign markers for that campaign on the map
* Campaign team members can add and remove pins for that campaign on the map
* Campaign team members can add a photo to a sign marker which is displayed in the marker info window (nice to have)

### Signs Screen
* Campaign team members can view that campaign's sign markers as a list
* Campaign team members can delete sign markers for that campaign
* The photo (if any) is displayed with each sign in the list (nice to have)


## Version 3
### Team Screen
* Admin can add people to a campaign as team members or spectators

### Campaign Screen
* Team members and spectators can view only the campaigns they are a part of

### Map Screen
* Spectators can only view the sign markers on the map, they cannot add or remove them

### Signs Screen
* Spectators can only view the sign list, they cannot remove them
