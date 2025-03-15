# Video Streaming API

## Overview

Our client, a major Hollywood production house, is building tooling for their next-generation video streaming platform. They have asked us to implement functionality for their content managers to publish videos, handle their metadata, and track view and impression metrics to monetize user viewership.

## Functional Requirements

The main purpose of the application is to:

- Store information related to videos.
- Stream video content.
- Track user engagement actions related to videos.

To perform the above functionality, you are expected to implement an API that allows users to:

- **Publish a video.**
- **Add and Edit metadata** associated with the video (e.g., Title, Synopsis, Director, Cast, Year of Release, Genre, Running time).
- **Delist (soft delete)** a video and its associated metadata.
- **Load a video** – return the video metadata and the corresponding content.
- **Play a video** – return the content related to a video. The content can be a simple string acting as a mock to the actual video content.
- **List all available videos** – This should return a subset of the video metadata: Title, Director, Main Actor, Genre, and Running Time.
- **Search for videos** based on some search/query criteria (e.g., movies directed by a specific director). The returned result-set should feature the same subset of metadata.
- **Retrieve the engagement statistics for a video**, where engagement can be split into:
  - **Impressions** – A client loading a video.
  - **Views** – A client playing a video.

## Technical Requirements

To meet the functional requirements, the following technical requirements should be met:

- **JDK 17** or higher (Open source and LTS versions are preferable).
- **Spring Boot 2.7** or higher.
- **Maven** or **Gradle** for dependency management and build lifecycle.
- **Unit and Integration Tests** to verify the functionality.
- Persist information in a database or datastore of your choice.
- Detailed documentation explaining any decisions or assumptions made, along with instructions on how to compile and run the solution.

