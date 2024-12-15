# CMakeLists.txt
cmake_minimum_required(VERSION 3.10.2)
project("opencraft")

# Specify the C++ standard
set(CMAKE_CXX_STANDARD 14)

# Add the C++ source file
add_library(native-lib SHARED
    src/main/cpp/native-lib.cpp)

# Find the required Android log library
find_library(log-lib log)

# Link the native library to the log library
target_link_libraries(native-lib ${log-lib})