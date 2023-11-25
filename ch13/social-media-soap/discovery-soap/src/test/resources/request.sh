curl --header "content-type: text/xml" -d @request.xml http://localhost:8081/ws | xmllint --format -

# for WSDL http://localhost:8081/ws/recommender.wsdl