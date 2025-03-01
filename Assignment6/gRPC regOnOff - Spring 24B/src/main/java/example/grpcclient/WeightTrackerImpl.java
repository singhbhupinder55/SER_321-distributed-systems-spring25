package example.grpcclient;

import io.grpc.stub.StreamObserver;
import service.*;
import service.WeightTrackerProto.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightTrackerImpl extends WeightTrackerGrpc.WeightTrackerImplBase {

    private final Map<String, List<Weight>> weightDatabase = new HashMap<>();

    @Override
    public void addWeight(AddWeightRequest request, StreamObserver<AddWeightResponse> responseObserver) {
        String name = request.getWeight().getName();
        Weight weightEntry = request.getWeight();

        // Store weight in the database
        weightDatabase.putIfAbsent(name, new ArrayList<>());
        weightDatabase.get(name).add(weightEntry);

        AddWeightResponse response = AddWeightResponse.newBuilder()
                .setIsSuccess(true)
                .setError("")  // No error
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getWeight(GetWeightRequest request, StreamObserver<GetWeightResponse> responseObserver) {
        String name = request.getName().toLowerCase();

        // Retrieve weight history
        List<Weight> weights = weightDatabase.getOrDefault(name, new ArrayList<>());

        GetWeightResponse.Builder responseBuilder = GetWeightResponse.newBuilder()
                .setIsSuccess(!weights.isEmpty())
                .addAllWeight(weights);

        if (weights.isEmpty()) {
            responseBuilder.setError("No weight data found for user: " + name);
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getBMI(BMIRequest request, StreamObserver<BMIResponse> responseObserver) {
        double weight = request.getWeight();
        double height = request.getHeight();
        String units = request.getUnits();

        double bmi = 0;
        if (height <= 0 || weight <= 0) {
            responseObserver.onNext(BMIResponse.newBuilder()
                    .setIsSuccess(false)
                    .setError("Invalid weight or height values")
                    .build());
            responseObserver.onCompleted();
            return;
        }

        if ("metric".equalsIgnoreCase(units)) {
            bmi = weight / (height * height);
        } else if ("imperial".equalsIgnoreCase(units)) {
            bmi = (weight / (height * height)) * 703;
        } else {
            responseObserver.onNext(BMIResponse.newBuilder()
                    .setIsSuccess(false)
                    .setError("Invalid unit system. Use 'metric' or 'imperial'.")
                    .build());
            responseObserver.onCompleted();
            return;
        }

        BMIResponse response = BMIResponse.newBuilder()
                .setIsSuccess(true)
                .setBMI(bmi)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
