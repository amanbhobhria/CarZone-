package com.example.techfarming.view.apmc




import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.techfarming.R



class ApmcFragment : Fragment() {
    private lateinit var spinnerBrand: Spinner
    private lateinit var spinnerYear: Spinner
    private lateinit var spinnerCarName: Spinner
    private lateinit var btnCalculatePrice: Button
    private lateinit var tvPrice: TextView

    // Example data
    private val brands = arrayOf("Maruti Suzuki", "Hyundai", "Tata", "Honda", "Mahindra")
    private val modelYears = arrayOf("2020", "2021","2022", "2023", "2024")
    private val carNames = mapOf(
        "Maruti Suzuki" to arrayOf("Swift", "Baleno", "WagonR", "Ciaz", "Ertiga"),
        "Hyundai" to arrayOf("Creta", "Verna", "i20", "Santro", "Venue"),
        "Tata" to arrayOf("Nexon", "Harrier", "Tiago", "Altroz", "Punch"),
        "Honda" to arrayOf("City", "Amaze", "Jazz", "WR-V", "Civic"),
        "Mahindra" to arrayOf("Thar", "Scorpio", "XUV300", "Bolero", "XUV700")
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


//        getApmc()
        val view = inflater.inflate(R.layout.fragment_apmc, container, false)

        // Initialize views
        spinnerBrand = view.findViewById(R.id.spinner_brand)
        spinnerYear = view.findViewById(R.id.spinner_year)
        spinnerCarName = view.findViewById(R.id.spinner_car_name)
        btnCalculatePrice = view.findViewById(R.id.btn_calculate_price)
        tvPrice = view.findViewById(R.id.tv_price)

        spinnerBrand.prompt = "Select Brand"
        spinnerYear.prompt = "Select Model Year"
        spinnerCarName.prompt = "Select Car Name"

        // Set up spinners
        setUpSpinners()

        // Set button click listener
        btnCalculatePrice.setOnClickListener {
            calculatePrice()


        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        }

    private fun setUpSpinners() {
        // Set up the brand spinner
        val brandAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, brands)
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerBrand.adapter = brandAdapter

        // Set up the model year spinner
        val yearAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, modelYears)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerYear.adapter = yearAdapter

        // Add a listener to the brand spinner to update the car names spinner
        spinnerBrand.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedBrand = brands[position]
                updateCarNamesSpinner(selectedBrand)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        // Initialize the car names spinner with the first brand's car names
        updateCarNamesSpinner(brands[0])
    }

    private fun updateCarNamesSpinner(brand: String) {
        val carNamesAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, carNames[brand] ?: emptyArray())
        carNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCarName.adapter = carNamesAdapter
    }

    private fun calculatePrice() {
        // Get selected parameters
        val selectedBrand = spinnerBrand.selectedItem as String
        val selectedYear = spinnerYear.selectedItem as String
        val selectedCarName = spinnerCarName.selectedItem as String

        // Calculate price based on selected parameters
        // This is a placeholder for your actual price calculation logic
        // For now, just return a dummy price in INR
        val priceInInr = calculatePriceForCar(selectedBrand, selectedYear, selectedCarName)

        // Display the calculated price
        tvPrice.text = "Price: ₹$priceInInr"
    }

    private fun calculatePriceForCar(brand: String, year: String, carName: String): Int {
        // Implement your price calculation logic here
        // For now, return a dummy price in INR
        // You can use the brand, year, and car name to determine the actual price
        return when (brand) {
            "Maruti Suzuki" -> when (carName) {
                "Swift" -> 700_000
                "Baleno" -> 800_000
                "WagonR" -> 500_000
                "Ciaz" -> 1_000_000
                "Ertiga" -> 900_000
                else -> 0
            }
            "Hyundai" -> when (carName) {
                "Creta" -> 1_300_000
                "Verna" -> 1_000_000
                "i20" -> 800_000
                "Santro" -> 600_000
                "Venue" -> 900_000
                else -> 0
            }
            "Tata" -> when (carName) {
                "Nexon" -> 1_100_000
                "Harrier" -> 1_700_000
                "Tiago" -> 600_000
                "Altroz" -> 800_000
                "Punch" -> 750_000
                else -> 0
            }
            "Honda" -> when (carName) {
                "City" -> 1_200_000
                "Amaze" -> 900_000
                "Jazz" -> 800_000
                "WR-V" -> 1_000_000
                "Civic" -> 2_000_000
                else -> 0
            }
            "Mahindra" -> when (carName) {
                "Thar" -> 1_500_000
                "Scorpio" -> 1_200_000
                "XUV300" -> 1_000_000
                "Bolero" -> 800_000
                "XUV700" -> 2_000_000
                else -> 0
            }
            else -> 0
        }
    }


}