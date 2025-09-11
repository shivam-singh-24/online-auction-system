const urlParams = new URLSearchParams(window.location.search);
const auctionId = urlParams.get("id");

fetch(`/api/auctions/${auctionId}`)
  .then(res => res.json())
  .then(item => {
    document.getElementById("auction-detail").innerHTML = `
      <img src="${item.imageUrl}" alt="${item.title}">
      <h2>${item.title}</h2>
      <p><strong>Description:</strong> ${item.description}</p>
      <p><strong>Starting Price:</strong> ₹${item.startingPrice}</p>
      <p><strong>Current Bid:</strong> ₹${item.currentBid}</p>
      <p><strong>Ends:</strong> ${new Date(item.endTime).toLocaleString()}</p>
      <p><strong>Status:</strong> ${item.status}</p>
    `;
  })
  .catch(err => {
    document.getElementById("auction-detail").innerHTML = "<p>Error loading auction details.</p>";
    console.error("Detail fetch error:", err);
  });
