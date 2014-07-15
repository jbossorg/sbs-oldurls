Jive SBS Plugin: Old URLs
===========

Plugin provides backward compatibility of migrated content into Jive SBS.
It's designed to provide such feature for more than one particular system.

Old URL Redirect
----------------

Redirect feature provides permanent redirect from old system to SBS content.
URL pattern is following:

	/oldurl/redirect.jspa?sid={system_id}&urltype={urltype}&p1={param1}&p2={param2}&p3={param3}

where:

 * sid - System ID (required)
 * urltype - URL type - used for System URL categorization like threads/posts/categories etc.
 * p1 - URL Parameter 1 (required)
 * p2 - URL Parameter 2
 * p3 - URL Parameter 3

In old system needs to have defined redirect rule which points to redirect.jspa action. Examples:

    RewriteRule /Community/(.*)                  http://community.jboss.org/oldurl/redirect.jspa?sid=1&p1=$1 [R=301,L]

    RewriteCond %{QUERY_STRING} messageID=(.*)
    RewriteRule ^/forums/message.jspa            https://community.jboss.org/oldurl/redirect.jspa?sid=2&urlType=2&p1=%1 [R=301,L]

System IDs and Old URLs management
----------------------------------

New system must be defined in plugin itself in OldUrlManager class - SYSTEM enumeration.
Old URLs are defined in DB and can be added by DB script or via DbOldUrlManager service which is preferred way because ID is generated by Jive subsystem.

Installation steps
------------------

1. Install the plugin via the administration console (System -> Plugins -> Add Plugin)
2. Restart SBS
3. Add redurect rule in former system to point to /oldurl/redirect.jspa action with proper parameters
