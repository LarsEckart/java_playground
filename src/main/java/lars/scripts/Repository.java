package lars.scripts;

import com.fasterxml.jackson.annotation.JsonProperty;

record Repository(@JsonProperty("ssh_url") String url, Owner owner) {

}
