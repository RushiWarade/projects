// console.log("contact js page");

const viewContactModal = document.getElementById("view_contact_modal");

const baseURL = "http://localhost:8080";

// options with default values
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: false,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
    id: 'view_contact_modal',
    override: true,
};

const contactModal = new Modal(viewContactModal, options, instanceOptions)


function openContactModal() {
    contactModal.show();
}

function closeContactModal() {
    contactModal.hide();
}

async function loadContactData(id) {
    console.log(id);
    try {
        const data = await (await fetch(`${baseURL}/api/contact/${id}`)).json();
        console.log(data);

        document.querySelector("#contact_name").innerHTML = data.name;
        document.querySelector("#contact_email").innerHTML = data.email;
        document.querySelector("#contact_contact").innerHTML = data.phoneNumber;
        document.querySelector("#contact_address").innerHTML = data.address;
        document.querySelector("#contact_desc").innerHTML = data.description;
        document.querySelector("#contact_linkdin").innerHTML = data.linkdinLink;
        document.querySelector("#contact_linkdin").href = data.linkdinLink;
        document.querySelector("#contact_website").innerHTML = data.websiteLink;
        const website = document.querySelector("#contact_website").innerHTML = data.websiteLink;
        document.querySelector("#contact_image").src = data.picture;

        // (website.data.websiteLink != "") ? website.style.display = none : website.style.display = "";

        openContactModal();
    } catch (error) {
        console.log(error);
    }
}



