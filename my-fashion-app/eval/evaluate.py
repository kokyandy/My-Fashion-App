import json
import requests
import os

# Configuration
API_URL = "http://localhost:8080/api/garments/upload"
DATA_DIR = "./data"
GROUND_TRUTH_FILE = "ground_truth.json"

def evaluate_model():
    with open(GROUND_TRUTH_FILE, 'r') as f:
        ground_truth = json.load(f)

    results = {"garmentType": {"correct": 0, "total": 0}, 
               "material": {"correct": 0, "total": 0}}

    print("🚀 Starting Model Evaluation...\n")

    for filename, truth_data in ground_truth.items():
        file_path = os.path.join(DATA_DIR, filename)
        if not os.path.exists(file_path):
            continue

        # Simulate calling local Java backend API
        with open(file_path, 'rb') as img:
            files = {'file': img}
            try:
                # Note: if the backend returns a hard-coded mock, the measured accuracy reflects the mock.
                # Mention in the README for interviews: the script is ready but not run against the full real model due to API quota limits.
                response = requests.post(API_URL, files=files)
                prediction = response.json()

                # Compare predictions with ground truth
                for field in results.keys():
                    predicted_val = prediction.get(field, "").lower()
                    truth_val = truth_data.get(field, "").lower()

                    if truth_val:
                        results[field]["total"] += 1
                        if truth_val in predicted_val: # simple substring match
                            results[field]["correct"] += 1

            except Exception as e:
                print(f"Error processing {filename}: {e}")

    # Print report
    print("📊 Evaluation Report:")
    for field, stats in results.items():
        if stats["total"] > 0:
            accuracy = (stats["correct"] / stats["total"]) * 100
            print(f"- {field.capitalize()} Accuracy: {accuracy:.2f}% ({stats['correct']}/{stats['total']})")

if __name__ == "__main__":
    evaluate_model()