## Example chat via browser console

### Step 1 : Add user 1 to chat
- Open new browser console tab and type: <br />
<code>
let user1 = new WebSocket("ws://localhost:8085/web-socket/user1")
</code>

### Step 2 : Add user 2 to chat
- Open new browser console tab and type: <br />
<code>
let user2 = new WebSocket("ws://localhost:8085/web-socket/user2")
</code>

### Step 3 : user1 sends hi to user2
- Tab 1: <br />
<code>
user1.send("{\"fromUserName\":\"user1\",\"toUserName\":\"user2\",\"message\":\"hi\"}")
</code>

### Step 4 : user2 sends hi to user1
- Tab 1: <br />
  <code>
  user2.send("{\"fromUserName\":\"user2\",\"toUserName\":\"user1\",\"message\":\"hi\"}")
  </code>

### Step 5 : user2 exits chat
- Tab 1: <br />
  <code>
  user2.close()
  </code>