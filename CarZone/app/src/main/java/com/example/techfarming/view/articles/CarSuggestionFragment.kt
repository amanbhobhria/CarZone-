package com.example.techfarming.view.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.techfarming.R


class CarSuggestionFragment : Fragment() {

    private lateinit var spinnerCategory: Spinner
    private lateinit var seekBarBudget: SeekBar
    private lateinit var textViewBudgetValue: TextView
    private lateinit var seekBarMileage: SeekBar
    private lateinit var textViewMileageValue: TextView
    private lateinit var seekBarServiceCost: SeekBar
    private lateinit var textViewServiceCostValue: TextView
    private lateinit var btnSuggestCar: Button
    private lateinit var tvSuggestedCar: TextView


    data class Car(
        val name: String,
        val category: String, // "Sedan" or "SUV" (you can add more categories if needed)
        val price: Int, // Price in INR
        val mileage: Double, // Mileage in km/l
        val serviceCost: Int // Service cost in INR/month

    )


    val availableCars = listOf(
        Car("Scorpio", "SUV", 1200000, 14.0, 5000),
        Car("Seltos", "SUV", 1500000, 17.0, 4000),
        Car("Creta", "SUV", 1400000, 15.0, 3500),
        Car("Verna", "Sedan", 1300000, 20.0, 2500),
        Car("Taigun", "SUV", 1600000, 16.5, 3000),
        Car("Swift", "Hatchback", 800000, 22.0, 1500),
        Car("Nexon", "SUV", 1300000, 17.0, 3500),
        Car("Baleno", "Hatchback", 700000, 22.5, 1200),
        Car("City", "Sedan", 1200000, 17.5, 3000),
        Car("Innova", "SUV", 1800000, 12.0, 4000),
        // Add more cars as needed
    )


    // Example data
    private val categories = arrayOf("Sedan", "SUV")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_car_suggestion, container, false)

        // Initialize views
        spinnerCategory = view.findViewById(R.id.spinner_category)
        seekBarBudget = view.findViewById(R.id.seekbar_budget)
        textViewBudgetValue = view.findViewById(R.id.textview_budget_value)
        seekBarMileage = view.findViewById(R.id.seekbar_mileage)
        textViewMileageValue = view.findViewById(R.id.textview_mileage_value)
        seekBarServiceCost = view.findViewById(R.id.seekbar_service_cost)
        textViewServiceCostValue = view.findViewById(R.id.textview_service_cost_value)
        btnSuggestCar = view.findViewById(R.id.btn_suggest_car)
        tvSuggestedCar = view.findViewById(R.id.tv_suggested_car)

        // Set up the category spinner
        setUpCategorySpinner()

        // Add listeners to update the TextViews when SeekBar values change
        setUpSeekBarListeners()

        // Set button click listener
        btnSuggestCar.setOnClickListener {
            suggestBestCar()
        }

        return view


    }


    private fun setUpCategorySpinner() {
        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = categoryAdapter
    }

    private fun setUpSeekBarListeners() {
        // Set up SeekBar listeners to update TextViews
        seekBarBudget.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textViewBudgetValue.text = "$progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekBarMileage.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textViewMileageValue.text = "$progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekBarServiceCost.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textViewServiceCostValue.text = "$progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun suggestBestCar() {
        // Get the selected filters
        val selectedCategory = spinnerCategory.selectedItem as String
        val selectedBudget = seekBarBudget.progress
        val selectedMileage = seekBarMileage.progress
        val selectedServiceCost = seekBarServiceCost.progress

        // Use the filters to suggest the best car under budget
        val suggestedCar = findBestCar(selectedCategory, selectedBudget, selectedMileage, selectedServiceCost)

        // Display the suggested car
        tvSuggestedCar.text = suggestedCar
    }

    fun findBestCar(category: String, budget: Int, mileage: Int, serviceCost: Int): String {
        // Filter cars based on the selected category (Sedan or SUV)
        val filteredCars = availableCars.filter { car ->
            car.category == category && // Filter by category
                    car.price <= budget && // Filter by budget
                    car.mileage >= mileage && // Filter by mileage
                    car.serviceCost <= serviceCost // Filter by service cost
        }

        // If there are no cars that meet the criteria, return a message
        if (filteredCars.isEmpty()) {
            return "No cars found that match your preferences."
        }

        // Rank the filtered cars by the closest match to the user's preferences
        // Calculate a score for each car based on how close it is to the user's preferences
        val rankedCars = filteredCars.map { car ->
            val priceDiff = budget - car.price // Smaller difference is better
            val mileageDiff = car.mileage - mileage // Smaller difference is better
            val serviceCostDiff = serviceCost - car.serviceCost // Smaller difference is better

            // Calculate a simple score based on differences
            val score = priceDiff + mileageDiff + serviceCostDiff

            car to score
        }

        // Sort the cars based on the calculated score (lowest score is the best match)
        val bestCar = rankedCars.minByOrNull { it.second }?.first

        // Return the name of the best car if found
        return bestCar?.name ?: "No cars found that match your preferences."
    }

}