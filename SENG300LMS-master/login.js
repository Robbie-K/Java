//Login Account

// Called on form submission from index.html

function logIn() {
	//Takes information from index.html as input for email and password.
	let email = document.getElementById('email');
	let password = document.getElementById('password');

	let success;

	let userQuery = database.collection("users").where("email", "==", email.value);
	userQuery.get().then(function(snapshot) {
		if (!snapshot.empty) {
			let user = snapshot.docs[0];

			let fetchedPassword = user.get("password");
			if (fetchedPassword == password.value) {
				let userId = user.get("id");

				if (userId >= 10099900 && userId < 10099999) {
					window.location.href = "admin.html?userId=" + userId;
				} else {
					window.location.href = "search.html?userId=" + userId;
				}

			} else {
				let errorSpan = document.getElementById("wrongInfo");
				errorSpan.style.display = 'inline';

				email.value = "";
				password.value = "";
			}
		}
	});
};

// makes it so that the search bar activitates when they hit "enter"
var input = document.getElementById("password");
input.addEventListener("keyup", function(event) {
  if (event.keyCode === 13) {
    event.preventDefault();
    document.getElementById("log").click();
  }
});
