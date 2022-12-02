# Automation of adding player to user's friend-list

The goal of the app is adding the predefined range of players to user's friend-list, 
because doing this manually is so boring.

## Overview

There are a few parameters that allow us to tune the process. Among them are 2 mandatory parameters and 7 optional.

### Required parameters.

**ERPK**. Is a parameter from cookies that are sent with almost every request to the eRepublik server. 
It allows sure that only the authorized user can perform read or edit sensitive data that belongs to him/her.

**TOKEN**. Is very similar to the **ERPK**, but is placed inside the body of the request.

The easiest way to find these arguments is to buy any good on the market. Here is the example in Chrome browser. 
Open Developer Tools and switch to the Network tab. Then go to the market, find any goods. 
Clear the history of all requests in the console with 🚫. Press the "Buy" button. 
In the console find the request named "marketplaceAction", and click on it. Choose the Headers tab, find the Request Headers section, find the "cookie" header and inside its value find the pair like "erpk=3do4mtja0bvnhrqansr184b4v3". The "3do4mtja0bvnhrqansr184b4v3" value is our ERPK parameter. Write it out somewhere. Next, switch to the Payload tab, find the _token key and copy its value. This is our TOKEN parameter. Write it out as well.

_Note. Remember that these two parameters are valid for 15 minutes only after they were last used, so make sure that you are prepared to use them prior to getting them._

### Optional parameters.

**FROM_ID**. Specifies from which ID of a player to start adding friends. The minimal value is 178. You may start from any you want, but it must be lower than TO_ID.

**TO_ID**. Specifies to which ID (including) of a player to add to friends. Must be bigger than FROM_ID. The default value is 9671074 (as of 2022-10-17).

**INCLUDE_COUNTRIES**. Specifies players of which countries add to friends. Should be written like `INCLUDE_COUNTRIES=1,38,40`. By default, is empty, which means the app will be adding all players to your friend list. The list of IDs of all countries you can find here.

**EXCLUDE_COUNTRIES**. Specifies players of which countries skip from adding to friends. Should be written like `EXCLUDE_COUNTRIES=13,41,44`. By default, is empty, which means the app will not exclude any players.

_NOTE. If you specify the same country for both lists, the INCLUDE_COUNTRIES will be taken into account only._

**ADD_DEAD**. Specifies whether to add dead players to your friend list. Should be written as follows: `ADD_DEAD=true`.

**ADD_BLOCKED**. Specifies whether to add blocked (by you) players to your friend list. Should be written as follows: `ADD_BLOCKED=true`.

**ADD_BANNED**. Probably will be removed later due to the lack of sense to add banned players to the friend list. Should be written as follows: `ADD_BANNED=true`.

## Running

### 



## Drawbacks/TODOs

Currently, we need to bypass all players ever registered. 
It would be very nice if we could bypass only those we really care. 
This is a topic for the future updates.
