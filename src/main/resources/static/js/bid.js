const auctionId = 1; // Replace with dynamic ID if needed

function loadBidHistory() {
  fetch(`/api/bids/history/${auctionId}`)
    .then(res => res.json())
    .then(data => {
      const list = document.getElementById("bid-history");
      list.innerHTML = "";
      data.forEach(bid => {
        list.innerHTML += `<li>₹${bid.amount} by ${bid.bidder.username} at ${bid.bidTime}</li>`;
      });
    });
}

function placeBid() {
  const amount = parseFloat(document.getElementById("bidAmount").value);
  const bidder = {
    username: "demoUser",
    password: "demoPass"
  };

  fetch(`/api/bids/place?auctionId=${auctionId}&amount=${amount}`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(bidder)
  })
  .then(res => res.json())
  .then(() => {
    alert("Bid placed successfully!");
    loadBidHistory();
  })
  .catch(err => alert("Bid failed: " + err));
}

document.addEventListener("DOMContentLoaded", () => {
  loadBidHistory();
});

