function loadUsers() {
  fetch("/api/admin/users")
    .then(res => res.json())
    .then(data => {
      const container = document.getElementById("admin-data");
      container.innerHTML = "<h3>Users</h3>";
      data.forEach(user => {
        container.innerHTML += `<p>${user.username} (${user.role})
          <button onclick="deleteUser(${user.id})">Delete</button></p>`;
      });
    });
}

function loadAuctions() {
  fetch("/api/admin/auctions")
    .then(res => res.json())
    .then(data => {
      const container = document.getElementById("admin-data");
      container.innerHTML = "<h3>Auctions</h3>";
      data.forEach(auction => {
        container.innerHTML += `<p>${auction.title} - ₹${auction.currentBid}
          [${auction.status}]
          <button onclick="closeAuction(${auction.id})">Close</button></p>`;
      });
    });
}

function deleteUser(id) {
  fetch(`/api/admin/user/${id}`, { method: "DELETE" })
    .then(() => {
      alert("User deleted");
      loadUsers();
    });
}

function closeAuction(id) {
  fetch(`/api/admin/close-auction/${id}`, { method: "PUT" })
    .then(res => res.json())
    .then(() => {
      alert("Auction closed");
      loadAuctions();
    });
}
function declareWinner(id) {
  fetch(`/api/admin/declare-winner/${id}`, { method: "PUT" })
    .then(res => res.json())
    .then(data => {
      alert("Winner declared: " + (data.winner ? data.winner.username : "No bids"));
      loadAuctions(); // refresh list
    });
}
