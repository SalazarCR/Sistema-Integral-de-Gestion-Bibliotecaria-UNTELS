@PutMapping("/status/{id}")
public ResponseEntity<String> changeStatus(
        @PathVariable Integer id,
        @RequestParam boolean status) {

    userService.changeStatus(id, status);
    return ResponseEntity.ok("Estado actualizado");
}