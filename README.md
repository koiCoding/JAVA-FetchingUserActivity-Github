# User Activity Fetching CLI - Github 

**UserFetchingCLI** is used to fetch user activity from Github v3 API and display recent user activity through console.

## Features

-Fetching user activity (push event)
-Display total commits & each commit details including author, url, commit message.

## Installation

1. Clone this repository:
   ```bash
   git clone https://github.com/koiCoding/JAVA-FetchingUserActivity-Github.git
   ```
2. Navigate to local repository:
   ```bash
   cd local_path
   ```
3. Run the following command in the terminal:
  ```bash
  java -cp "lib/json.jar;." GithubUserActivity username
  ```

4. Output instance:
   ```bash
   Event Type: PushEvent
    Repository: koiCoding/PROJECT_WEB_DEVHUB
      Commit 1:
        Message: Update README.md
        Author: koiCoding
        Commit URL: https://api.github.com/repos/koiCoding/PROJECT_WEB_DEVHUB/commits/eb2f0d0fc9707e32e817c6879db70f4dabf89ee9
    Created At: 2024-12-20T07:25:11Z
   --------------------------------------
   Total number of commits: 6
   ```


