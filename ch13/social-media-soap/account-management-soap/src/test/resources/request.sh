curl --header "content-type: text/xml" -d @request.xml http://localhost:8080/ws | xmllint --format -

# for WSDL http://localhost:8080/ws/followers.wsdl